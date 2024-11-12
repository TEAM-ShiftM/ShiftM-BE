package com.shiftm.shiftm.domain.company.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.company.dao.CompanyFinder;
import com.shiftm.shiftm.domain.company.dao.CompanyRepository;
import com.shiftm.shiftm.domain.company.domain.Company;
import com.shiftm.shiftm.domain.company.dto.request.CreateCompanyRequest;
import com.shiftm.shiftm.domain.company.dto.response.CompanyResponse;
import com.shiftm.shiftm.domain.company.exception.AlreadyCompanyExistException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminCompanyService {
	private final CompanyRepository companyRepository;
	private final CompanyFinder companyFinder;

	@Transactional
	public CompanyResponse createCompany(CreateCompanyRequest requestDto) {
		if (companyFinder.isExistCompany()) {
			throw new AlreadyCompanyExistException();
		}

		Company company = companyRepository.save(requestDto.toEntity());

		return new CompanyResponse(company);
	}

	@Transactional(readOnly = true)
	public CompanyResponse getCompany() {
		return new CompanyResponse(companyFinder.getCompany());
	}
}
