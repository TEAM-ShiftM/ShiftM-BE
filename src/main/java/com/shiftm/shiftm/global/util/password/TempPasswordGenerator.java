package com.shiftm.shiftm.global.util.password;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class TempPasswordGenerator {
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-";
	private static final int PASSWORD_LENGTH = 8;
	private static final SecureRandom random = new SecureRandom();

	public String generateTemporaryPassword() {
		StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = random.nextInt(CHARACTERS.length());
			password.append(CHARACTERS.charAt(index));
		}

		return password.toString();
	}
}
