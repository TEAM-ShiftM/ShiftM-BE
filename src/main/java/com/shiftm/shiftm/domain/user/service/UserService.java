package com.shiftm.shiftm.domain.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.auth.exception.UserNotFoundException;
import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.domain.enums.Gender;
import com.shiftm.shiftm.domain.user.domain.enums.Role;
import com.shiftm.shiftm.domain.user.domain.enums.Status;
import com.shiftm.shiftm.domain.user.dto.request.SignUpRequest;
import com.shiftm.shiftm.domain.user.dto.request.UpdateProfileRequest;
import com.shiftm.shiftm.domain.user.exception.EmailDuplicateException;
import com.shiftm.shiftm.domain.user.exception.IdDuplicateException;
import com.shiftm.shiftm.domain.user.exception.InvalidCompanyIdException;
import com.shiftm.shiftm.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${shiftm.company.key}")
	private String companyKey;

	@Transactional
	public User signUp(SignUpRequest requestDto) {
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

	public User getProfile(String userId) {
		return getUser(userId);
	}

	@Transactional
	public User updateProfile(String userId, UpdateProfileRequest requestDto) {
		User user = getUser(userId);

		user.setEmail(requestDto.email());
		user.setName(requestDto.name());
		user.setBirthDate(requestDto.birthDate());
		user.setGender(Gender.valueOf(requestDto.gender().toUpperCase()));

		return user;
	}

	@Transactional
	public void withdraw(String userId) {
		User user = getUser(userId);

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

	public User getUser(String userId) {
		Optional<User> optionalUser = userRepository.findById(userId);

		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException(userId);
		}

		return optionalUser.get();
	}
}
