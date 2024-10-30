package com.shiftm.shiftm.domain.shift.dto.response;

public record ShiftTimeOfWeekResponse(
	long hour,
	long minute
) {
	public ShiftTimeOfWeekResponse(long weeklyShiftTime) {
		this(weeklyShiftTime / 60, weeklyShiftTime % 60);
	}
}
