package com.shiftm.shiftm.domain.user.dto.response;

import java.time.LocalDate;

import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.domain.enums.Gender;

public record UserResponse(
	String id,
	String email,
	String name,
	LocalDate birthDate,
	Gender gender
) {
	public UserResponse(User user) {
		this(user.getId(), user.getEmail(), user.getName(), user.getBirthDate(), user.getGender());
	}
}
