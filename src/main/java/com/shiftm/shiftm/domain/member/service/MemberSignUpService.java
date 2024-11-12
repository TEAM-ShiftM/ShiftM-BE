package com.shiftm.shiftm.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.member.dao.MemberFinder;
import com.shiftm.shiftm.domain.member.dao.MemberRepository;
import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.domain.enums.Role;
import com.shiftm.shiftm.domain.member.dto.request.SignUpRequest;
import com.shiftm.shiftm.domain.member.dto.response.CheckResponse;
import com.shiftm.shiftm.domain.member.dto.response.MemberResponse;
import com.shiftm.shiftm.domain.member.exception.EmailDuplicateException;
import com.shiftm.shiftm.domain.member.exception.IdDuplicateException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberSignUpService {
	private final MemberRepository memberRepository;
	private final MemberFinder memberFinder;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public MemberResponse signUp(SignUpRequest requestDto) {
		validateSignUpRequest(requestDto);

		String password = passwordEncoder.encode(requestDto.password());
		Member member = memberRepository.save(requestDto.toEntity(password, Role.ROLE_USER));

		return new MemberResponse(member);
	}

	@Transactional(readOnly = true)
	public CheckResponse checkUniqueId(String id) {
		return new CheckResponse(!memberFinder.isExistedId(id));
	}

	@Transactional
	public void sendEmailVerificationCode(String email) {
		if (memberFinder.isExistedEmail(email)) {
			throw new EmailDuplicateException(email);
		}

		emailService.sendEmailVerificationCode(email);
	}

	@Transactional(readOnly = true)
	public CheckResponse verifyEmailCode(String email, String verificationCode) {
		return new CheckResponse(emailService.verifyEmailCode(email, verificationCode));
	}

	private void validateSignUpRequest(SignUpRequest requestDto) {
		if (memberFinder.isExistedId(requestDto.id())) {
			throw new IdDuplicateException(requestDto.id());
		}

		if (memberFinder.isExistedEmail(requestDto.email())) {
			throw new EmailDuplicateException(requestDto.email());
		}
	}
}
