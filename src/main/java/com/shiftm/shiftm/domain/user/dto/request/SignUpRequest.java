package com.shiftm.shiftm.domain.user.dto.request;

import java.time.LocalDate;

import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.domain.enums.Gender;
import com.shiftm.shiftm.domain.user.domain.enums.Role;
import com.shiftm.shiftm.domain.user.domain.enums.Status;

public record SignUpRequest(
	String id,
	String password,
	String rePassword,
	String companyId,
	String email,
	String verificationNumber,
	String name,
	LocalDate birthDate,
	String gender
) {
	public User toEntity(String password, Role role) {
		return User.builder()
			.id(id)
			.password(password)
			.email(email)
			.name(name)
			.birthDate(birthDate)
			.gender(Gender.valueOf(gender.toUpperCase()))
			.status(Status.ACTIVE)
			.role(role)
			.build();
	}
}
