package com.shiftm.shiftm.domain.leave.dto.request;

import java.time.LocalDate;

public record UpdateLeaveRequest(
	LocalDate expirationDate,
	int count
) {
}
