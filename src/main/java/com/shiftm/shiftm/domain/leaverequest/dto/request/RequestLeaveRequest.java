package com.shiftm.shiftm.domain.leaverequest.dto.request;

import java.time.LocalDate;

public record RequestLeaveRequest(
	LocalDate startDate,
	LocalDate endDate
) {
}
