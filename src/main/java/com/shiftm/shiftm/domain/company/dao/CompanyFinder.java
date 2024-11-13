package com.shiftm.shiftm.domain.company.dao;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.company.domain.Company;
import com.shiftm.shiftm.domain.company.exception.CompanyNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CompanyFinder {
	private final CompanyRepository companyRepository;

	@Transactional(readOnly = true)
	public Company getCompany() {
		Optional<Company> optionalCompany = companyRepository.findById(1L);

		if (optionalCompany.isEmpty()) {
			throw new CompanyNotFoundException();
		}

		return optionalCompany.get();
	}

	@Transactional(readOnly = true)
	public LocalTime getCheckInTime() {
		return companyRepository.findCheckInTime();
	}

	@Transactional(readOnly = true)
	public boolean isExistCompany() {
		return companyRepository.existsById(1L);
	}
}
