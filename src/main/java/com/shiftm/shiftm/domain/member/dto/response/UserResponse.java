package com.shiftm.shiftm.domain.member.dto.response;

import java.time.LocalDate;

import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.domain.enums.Gender;

public record UserResponse(
	String id,
	String email,
	String name,
	LocalDate birthDate,
	Gender gender
) {
	public UserResponse(Member user) {
		this(user.getId(), user.getEmail(), user.getName(), user.getBirthDate(), user.getGender());
	}
}
