package com.shiftm.shiftm.domain.user.service;

import java.time.Duration;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.auth.service.RedisService;
import com.shiftm.shiftm.domain.user.exception.EmailDuplicateException;
import com.shiftm.shiftm.domain.user.repository.UserRepository;
import com.shiftm.shiftm.infra.email.MailSender;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {
	private final UserRepository userRepository;
	private final RedisService redisService;
	private final MailSender mailSender;

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
}
