package com.shiftm.shiftm.domain.shift.domain;

import java.time.LocalDateTime;

import com.shiftm.shiftm.domain.shift.domain.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CheckOut {
	@Column
	private LocalDateTime checkOutTime;

	@Column
	private Status checkOutStatus;

	@Builder
	public CheckOut(LocalDateTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
}
