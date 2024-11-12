package com.shiftm.shiftm.domain.company.dto.response;

import java.time.LocalTime;

import com.shiftm.shiftm.domain.company.domain.Company;

public record CompanyResponse(
	String companyId,
	LocalTime checkInTime,
	LocalTime checkOutTime,
	int breakTime,
	String companyIP
) {
	public CompanyResponse(Company company) {
		this(company.getCompanyId(), company.getCheckInTime(),company.getCheckOutTime(),
			company.getBreakTime(), company.getCompanyIP());
	}
}
