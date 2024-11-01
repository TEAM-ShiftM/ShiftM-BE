package com.shiftm.shiftm.domain.member.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.auth.exception.UserNotFoundException;
import com.shiftm.shiftm.domain.member.dao.MemberFinder;
import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.domain.enums.Gender;
import com.shiftm.shiftm.domain.member.domain.enums.Role;
import com.shiftm.shiftm.domain.member.domain.enums.Status;
import com.shiftm.shiftm.domain.member.dto.request.SignUpRequest;
import com.shiftm.shiftm.domain.member.dto.request.UpdateProfileRequest;
import com.shiftm.shiftm.domain.member.dto.response.MemberResponse;
import com.shiftm.shiftm.domain.member.exception.EmailDuplicateException;
import com.shiftm.shiftm.domain.member.exception.IdDuplicateException;
import com.shiftm.shiftm.domain.member.dao.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final MemberFinder memberFinder;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public MemberResponse signUp(SignUpRequest requestDto) {
		validateSignUpRequest(requestDto);

		String password = passwordEncoder.encode(requestDto.password());
		Member member = memberRepository.save(requestDto.toEntity(password, Role.USER));

		return new MemberResponse(member);
	}

	private void validateSignUpRequest(SignUpRequest requestDto) {
		if (memberFinder.isExistedId(requestDto.id())) {
			throw new IdDuplicateException(requestDto.id());
		}

		if (memberFinder.isExistedEmail(requestDto.email())) {
			throw new EmailDuplicateException(requestDto.email());
		}
	}

	public Member getProfile(String userId) {
		return getUser(userId);
	}

	@Transactional
	public Member updateProfile(String userId, UpdateProfileRequest requestDto) {
		Member user = getUser(userId);

		user.setEmail(requestDto.email());
		user.setName(requestDto.name());
		user.setBirthDate(requestDto.birthDate());
		user.setGender(Gender.valueOf(requestDto.gender().toUpperCase()));

		return user;
	}

	@Transactional
	public void withdraw(String userId) {
		Member user = getUser(userId);

		user.setStatus(Status.INACTIVE);
	}

	public Member getUser(String userId) {
		Optional<Member> optionalUser = memberRepository.findById(userId);

		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException(userId);
		}

		return optionalUser.get();
	}
}
