package com.shiftm.shiftm.domain.member.service;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.auth.exception.UserNotFoundException;
import com.shiftm.shiftm.domain.auth.service.RedisService;
import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.exception.EmailDuplicateException;
import com.shiftm.shiftm.domain.member.dao.MemberRepository;
import com.shiftm.shiftm.global.util.password.TempPasswordGenerator;
import com.shiftm.shiftm.infra.email.MailSender;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {
	private final MemberRepository userRepository;
	private final RedisService redisService;
	private final MailSender mailSender;
	private final TempPasswordGenerator tempPasswordGenerator;
	private final PasswordEncoder passwordEncoder;

	private static final String VERIFICATION_CODE_PREFIX = "Verification Code ";
	private static final long VERIFICATION_CODE_EXPIRATION_TIME = 1000 * 60 * 5;

	@Transactional
	public void sendEmailVerificationCode(String email) {
		if (isEmailDuplicated(email)) {
			throw new EmailDuplicateException(email);
		}

		String verificationCode = createVerificationCode();

		redisService.saveValues(VERIFICATION_CODE_PREFIX + email, verificationCode, Duration.ofMillis(VERIFICATION_CODE_EXPIRATION_TIME));

		mailSender.sendMail(email, "ShiftM 이메일 인증 번호", verificationCode);
	}

	@Transactional
	public boolean verifyEmailCode(String email, String verificationCode) {
		String storedVerificationCode = redisService.getValues(VERIFICATION_CODE_PREFIX + email);

		return storedVerificationCode.equals(verificationCode);
	}

	public void findId(String email) {
		Member user = getUser(email);

		mailSender.sendMail(email, "ShiftM 아이디 찾기", user.getId());
	}

	@Transactional
	public void findPassword(String id, String email) {
		Member user = getUser(email);

		if (!isIdEquals(id, user.getId())) {
			throw new UserNotFoundException(id);
		}

		String tempPassword = tempPasswordGenerator.generateTemporaryPassword();

		user.setPassword(passwordEncoder.encode(tempPassword));

		mailSender.sendMail(email, "ShiftM 임시 비밀번호", tempPassword);
	}

	private String createVerificationCode() {
		Random random = new Random();
		StringBuilder verificationCode = new StringBuilder();

		for (int i = 0; i < 6; i++) {
			verificationCode.append(random.nextInt(10));
		}

		return verificationCode.toString();
	}

	private boolean isEmailDuplicated(String email) {
		return userRepository.existsByEmail(email);
	}

	private boolean isIdEquals(String id, String storedId) {
		return id.equals(storedId);
	}

	private Member getUser(String email) {
		Optional<Member> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException(email);
		}

		return optionalUser.get();
	}
}
