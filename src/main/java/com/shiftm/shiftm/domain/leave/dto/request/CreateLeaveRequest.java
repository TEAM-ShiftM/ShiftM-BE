package com.shiftm.shiftm.domain.leave.dto.request;

import java.time.LocalDate;

public record CreateLeaveRequest(
	LocalDate expirationDate,
	int count,
	String userId
) {
}
