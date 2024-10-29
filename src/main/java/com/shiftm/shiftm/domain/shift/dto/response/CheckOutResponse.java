package com.shiftm.shiftm.domain.shift.dto.response;

import java.time.LocalDateTime;

import com.shiftm.shiftm.domain.shift.domain.Shift;

public record CheckOutResponse(
	long id,
	LocalDateTime checkoutTime
) {
	public CheckOutResponse(Shift shift) {
		this(shift.getId(), shift.getCheckout().getCheckoutTime());
	}
}
