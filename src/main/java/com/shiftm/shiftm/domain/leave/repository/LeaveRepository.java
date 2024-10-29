package com.shiftm.shiftm.domain.leave.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiftm.shiftm.domain.leave.domain.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
}
