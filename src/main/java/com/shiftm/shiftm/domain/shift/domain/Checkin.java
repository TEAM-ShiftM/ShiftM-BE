package com.shiftm.shiftm.domain.shift.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Checkin {
	@Column(nullable = false)
	private LocalDateTime checkinTime;

	@Column
	private Double latitude;

	@Column
	private Double longitude;

	@Column(nullable = false)
	private Boolean approval;

	@Builder
	public Checkin(LocalDateTime checkinTime, Boolean approval) {
		this.checkinTime = checkinTime;
		this.approval = approval;
	}
}
