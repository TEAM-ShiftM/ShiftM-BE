package com.shiftm.shiftm.domain.user.dto.request;

import java.time.LocalDate;

import com.shiftm.shiftm.domain.user.domain.enums.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateProfileRequest(
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
}
