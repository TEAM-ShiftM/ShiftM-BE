package com.shiftm.shiftm.domain.shift.dto.response;

import java.time.LocalDateTime;

import com.shiftm.shiftm.domain.shift.domain.Shift;

public record CheckInResponse(
	long id,
	LocalDateTime checkinTime
) {
	public CheckInResponse(Shift shift) {
		this(shift.getId(), shift.getCheckin().getCheckinTime());
	}
}
