package com.shiftm.shiftm.domain.leave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiftm.shiftm.domain.leave.domain.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
	List<Leave> findByUserId(String userId);
}
