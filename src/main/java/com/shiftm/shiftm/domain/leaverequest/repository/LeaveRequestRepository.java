package com.shiftm.shiftm.domain.leaverequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiftm.shiftm.domain.leaverequest.domain.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
}
