package com.shiftm.shiftm.domain.member.dto.response;

import java.time.LocalDate;

import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.domain.enums.Gender;

public record MemberResponse(
	String id,
	String email,
	String name,
	LocalDate birthDate,
	Gender gender
) {
	public MemberResponse(Member user) {
		this(user.getId(), user.getEmail(), user.getName(), user.getBirthDate(), user.getGender());
	}
}
