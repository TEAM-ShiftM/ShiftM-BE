package com.shiftm.shiftm.domain.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiftm.shiftm.domain.company.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
