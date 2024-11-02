package com.shiftm.shiftm.infra.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MailSender {
	private final JavaMailSender javaMailSender;

	public void sendMail(String to, String title, String text) {
		try {
			MimeMessage mailMessage = createMailMessage(to, title, text);
			javaMailSender.send(mailMessage);
		} catch (Exception e) {
			throw new UnableToSendEmailException();
		}
	}

	private MimeMessage createMailMessage(String to, String title, String text) throws MessagingException {
		MimeMessage mailMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mailMessage, false, "UTF-8");

		helper.setTo(to);
		helper.setSubject(title);
		helper.setText(text, true);

		return mailMessage;
	}
}
