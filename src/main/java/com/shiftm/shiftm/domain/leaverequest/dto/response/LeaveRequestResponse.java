package com.shiftm.shiftm.domain.leaverequest.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.shiftm.shiftm.domain.leaverequest.domain.LeaveRequest;

public record LeaveRequestResponse(
	long id,
	LocalDate startDate,
	LocalDate endDate,
	boolean approval,
	LocalDateTime createdAt,
	String userId,
	int count
) {
	public LeaveRequestResponse(LeaveRequest leaveRequest) {
		this(leaveRequest.getId(), leaveRequest.getStartDate(), leaveRequest.getEndDate(),
			leaveRequest.getApproval(), leaveRequest.getCreatedAt(),
			leaveRequest.getUser().getId(), leaveRequest.getLeave().getCount());
	}
}
