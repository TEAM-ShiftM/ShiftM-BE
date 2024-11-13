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
public class CheckIn {
	@Column(nullable = false)
	private LocalDateTime checkInTime;

	@Column(nullable = false)
	private Status checkInStatus;

	@Column
	private Double latitude;

	@Column
	private Double longitude;

	@Builder
	public CheckIn(LocalDateTime checkInTime, Status checkInStatus) {
		this.checkInTime = checkInTime;
		this.checkInStatus = checkInStatus;
	}
}
