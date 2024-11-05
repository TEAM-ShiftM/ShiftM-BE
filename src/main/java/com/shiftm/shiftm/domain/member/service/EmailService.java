package com.shiftm.shiftm.domain.member.service;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.member.exception.UserNotFoundException;
import com.shiftm.shiftm.domain.auth.service.RedisService;
import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.dao.MemberRepository;
import com.shiftm.shiftm.domain.member.exception.VerificationCodeNotFoundException;
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

	@Value("${email.verification.code.expiration}")
	private long verificationCodeExpirationTime;

	private static final String VERIFICATION_CODE_PREFIX = "Verification Code ";

	@Transactional
	public void sendEmailVerificationCode(String email) {
		String verificationCode = createVerificationCode();

		redisService.saveValues(VERIFICATION_CODE_PREFIX + email, verificationCode, Duration.ofMillis(verificationCodeExpirationTime));

		String emailMessage = createEmailMessage("ShiftM 이메일 인증 번호", "아래 인증 번호로 이메일 인증을 해주세요.", verificationCode);
		mailSender.sendMail(email, "[ShiftM] 이메일 인증 번호", emailMessage);
	}

	@Transactional(readOnly = true)
	public boolean verifyEmailCode(String email, String verificationCode) {
		String storedVerificationCode = redisService.getValues(VERIFICATION_CODE_PREFIX + email);

		if (storedVerificationCode.equals("false")) {
			throw new VerificationCodeNotFoundException();
		}

		return storedVerificationCode.equals(verificationCode);
	}

	public void sendEmailId(String email, String userId) {
		String emailMessage = createEmailMessage("ShiftM 아이디 찾기", "아이디는 아래와 같습니다.", userId);
		mailSender.sendMail(email,"[ShiftM] 아이디 찾기", emailMessage);
	}

	private String createVerificationCode() {
		Random random = new Random();
		StringBuilder verificationCode = new StringBuilder();

		for (int i = 0; i < 6; i++) {
			verificationCode.append(random.nextInt(10));
		}

		return verificationCode.toString();
	}

	private String createEmailMessage(String title, String message, String content) {
		StringBuilder emailMessage = new StringBuilder();

		emailMessage.append("<table text-align='center' cellpadding='0' cellspacing='0' width='100%' ");
		emailMessage.append("style='max-width: 600px; background-color: #FFFFFF; border: 1px solid #DDDDDD; border-radius: 8px; margin: 20px auto; padding: 20px;'>");
		emailMessage.append("<tr>");
		emailMessage.append("<td text-align='center' style='padding: 20px;'>");
		emailMessage.append("<h2 style='color: #333333; margin: 0;'>" + title + "</h2>");
		emailMessage.append("<p style='color: #666666; font-size: 16px;'>" + message + "</p>");
		emailMessage.append("</td>");
		emailMessage.append("</tr>");
		emailMessage.append("<tr>");
		emailMessage.append("<td style='text-align: center; padding: 20px 0;'>");
		emailMessage.append("<span style='display: inline-block; font-size: 24px; color: #333333;'>" + content + "</span>");
		emailMessage.append("</td>");
		emailMessage.append("<tr>");
		emailMessage.append("<td style='padding: 20px; color: #333333; font-size: 16px;'>");
		emailMessage.append("<p style='margin: 0;'>만약 해당 메일을 요청하지 않았으면 무시해주시기 바랍니다.</p>");
		emailMessage.append("<p style='margin: 0;'>감사합니다.</p>");
		emailMessage.append("<p style='margin: 10px 0 0 0;'>Team ShiftM</p>");
		emailMessage.append("</td>");
		emailMessage.append("</tr>");
		emailMessage.append("</table>");

		return emailMessage.toString();
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
