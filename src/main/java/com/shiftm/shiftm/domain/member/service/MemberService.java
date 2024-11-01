package com.shiftm.shiftm.domain.member.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.auth.exception.UserNotFoundException;
import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.domain.enums.Gender;
import com.shiftm.shiftm.domain.member.domain.enums.Role;
import com.shiftm.shiftm.domain.member.domain.enums.Status;
import com.shiftm.shiftm.domain.member.dto.request.SignUpRequest;
import com.shiftm.shiftm.domain.member.dto.request.UpdateProfileRequest;
import com.shiftm.shiftm.domain.member.exception.EmailDuplicateException;
import com.shiftm.shiftm.domain.member.exception.IdDuplicateException;
import com.shiftm.shiftm.domain.member.exception.InvalidCompanyIdException;
import com.shiftm.shiftm.domain.member.dao.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${shiftm.company.key}")
	private String companyKey;

	@Transactional
	public Member signUp(SignUpRequest requestDto) {
		if (isIdDuplicated(requestDto.id())) {
			throw new IdDuplicateException(requestDto.id());
		}

		if (isEmailDuplicated(requestDto.email())) {
			throw new EmailDuplicateException(requestDto.email());
		}

		if (!isValidCompanyId(requestDto.companyId())) {
			throw new InvalidCompanyIdException(requestDto.companyId());
		}

		String password = passwordEncoder.encode(requestDto.password());
		return userRepository.save(requestDto.toEntity(password, Role.USER));
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

	public boolean isIdDuplicated(String id) {
		return userRepository.existsById(id);
	}

	private boolean isEmailDuplicated(String email) {
		return userRepository.existsByEmail(email);
	}

	private boolean isValidCompanyId(String companyId) {
		return companyId.equals(companyKey);
	}

	public Member getUser(String userId) {
		Optional<Member> optionalUser = userRepository.findById(userId);

		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException(userId);
		}

		return optionalUser.get();
	}
}
