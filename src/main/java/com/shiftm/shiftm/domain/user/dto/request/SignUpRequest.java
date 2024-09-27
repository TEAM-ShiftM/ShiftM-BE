package com.shiftm.shiftm.domain.user.dto.request;

import java.time.LocalDate;

import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.domain.enums.Gender;
import com.shiftm.shiftm.domain.user.domain.enums.Role;
import com.shiftm.shiftm.domain.user.domain.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
	@Pattern(regexp = "^[a-z0-9_-]{5,15}$")
	String id,
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_-]{8,20}")
	String password,
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_-]{8,20}")
	String rePassword,
	@NotBlank
	String companyId,
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
	String email,
	@NotBlank
	String verificationNumber,
	@NotBlank
	String name,
	@NotNull
	LocalDate birthDate,
	@NotBlank
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
