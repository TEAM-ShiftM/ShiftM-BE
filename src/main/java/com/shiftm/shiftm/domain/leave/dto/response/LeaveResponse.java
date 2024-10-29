package com.shiftm.shiftm.domain.leave.dto.response;

import java.time.LocalDate;

import com.shiftm.shiftm.domain.leave.domain.Leave;

public record LeaveResponse(
	long id,
	LocalDate expirationDate,
	int count,
	String userId
) {
	public LeaveResponse(Leave leave) {
		this(leave.getId(), leave.getExpirationDate(), leave.getCount(), leave.getUser().getId());
	}
}
