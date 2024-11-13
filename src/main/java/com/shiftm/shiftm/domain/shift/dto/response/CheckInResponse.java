package com.shiftm.shiftm.domain.shift.dto.response;

import java.time.LocalDateTime;

import com.shiftm.shiftm.domain.shift.domain.Shift;
import com.shiftm.shiftm.domain.shift.domain.enums.Status;

public record CheckInResponse(
	long id,
	LocalDateTime checkinTime,
	Status checkInStatus
) {
	public CheckInResponse(Shift shift) {
		this(shift.getId(), shift.getCheckIn().getCheckInTime(), shift.getCheckIn().getCheckInStatus());
	}
}
