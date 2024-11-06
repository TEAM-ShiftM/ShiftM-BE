package com.shiftm.shiftm.domain.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.member.dao.MemberFinder;
import com.shiftm.shiftm.domain.member.dto.response.MemberResponse;
import com.shiftm.shiftm.domain.member.exception.UserNotFoundException;
import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.domain.enums.Gender;
import com.shiftm.shiftm.domain.member.dto.request.UpdateProfileRequest;
import com.shiftm.shiftm.domain.member.dao.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final MemberFinder memberFinder;
	private final EmailService emailService;

	@Transactional(readOnly = true)
	public void findId(String email) {
		Member member = memberFinder.getUserByEmail(email);

		emailService.sendEmailId(email, member.getId());
	}

	@Transactional(readOnly = true)
	public MemberResponse getProfile(String userId) {
		return new MemberResponse(memberFinder.getUser(userId));
	}

	@Transactional
	public MemberResponse updateProfile(String userId, UpdateProfileRequest requestDto) {
		Member member = memberFinder.getUser(userId);

		member.updateEmail(requestDto.email())
			  .updateName(requestDto.name())
			  .updateBirthDate(requestDto.birthDate())
			  .updateGender(Gender.valueOf(requestDto.gender().toUpperCase()));

		return new MemberResponse(member);
	}

	@Transactional
	public void withdraw(String userId) {
		Member member = memberFinder.getUser(userId);

		member.withdraw();
	}

	public Member getUser(String userId) {
		Optional<Member> optionalUser = memberRepository.findById(userId);

		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException(userId);
		}

		return optionalUser.get();
	}
}
