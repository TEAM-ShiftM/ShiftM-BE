package com.shiftm.shiftm.domain.company.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CompanyFinder {
	private final CompanyRepository companyRepository;

	@Transactional(readOnly = true)
	public boolean isExistCompany() {
		return companyRepository.existsById(1L);
	}
}
