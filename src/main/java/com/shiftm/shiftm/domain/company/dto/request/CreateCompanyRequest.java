package com.shiftm.shiftm.domain.company.dto.request;

import java.time.LocalTime;

import com.shiftm.shiftm.domain.company.domain.Company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCompanyRequest(
	@NotBlank
	String companyId,
	@NotNull
	LocalTime checkInTime,
	@NotNull
	LocalTime checkOutTime,
	@NotNull
	int breakTime,
	@NotNull
	String companyIP
) {
	public Company toEntity() {
		return Company.builder()
			.companyId(companyId)
			.checkInTime(checkInTime)
			.checkOutTime(checkOutTime)
			.breakTime(breakTime)
			.companyIP(companyIP)
			.build();
	}
}
