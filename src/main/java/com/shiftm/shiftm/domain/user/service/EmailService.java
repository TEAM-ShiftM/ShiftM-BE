package com.shiftm.shiftm.domain.user.service;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.user.exception.EmailDuplicateException;
import com.shiftm.shiftm.domain.user.repository.UserRepository;
import com.shiftm.shiftm.infra.email.MailSender;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {
	private final UserRepository userRepository;
	private final MailSender mailSender;

	@Transactional
	public void sendEmailVerificationCode(String email) {
		if (isEmailDuplicated(email)) {
			throw new EmailDuplicateException(email);
		}

		String verificationCode = createVerificationCode();

		mailSender.sendMail(email, "ShiftM 이메일 인증 번호", verificationCode);
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
