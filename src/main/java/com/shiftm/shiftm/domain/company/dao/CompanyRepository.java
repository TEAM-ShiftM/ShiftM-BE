package com.shiftm.shiftm.domain.company.dao;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shiftm.shiftm.domain.company.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	@Query("SELECT c.checkInTime FROM Company c")
	LocalTime findCheckInTime();
}
