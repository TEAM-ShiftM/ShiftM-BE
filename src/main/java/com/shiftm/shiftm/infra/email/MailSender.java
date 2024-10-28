package com.shiftm.shiftm.infra.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MailSender {
	private final JavaMailSender javaMailSender;

	public void sendMail(String to, String title, String text) {
		SimpleMailMessage mailMessage = createMailMessage(to, title, text);

		try {
			javaMailSender.send(mailMessage);
		} catch (RuntimeException e) {
			throw new UnableToSendEmailException();
		}
	}

	private SimpleMailMessage createMailMessage(String to, String title, String text) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(to);
		mailMessage.setSubject(title);
		mailMessage.setText(text);

		return mailMessage;
	}
}
