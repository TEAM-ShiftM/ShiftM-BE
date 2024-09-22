package com.shiftm.shiftm.domain.company.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime checkinTime;

	@Column(nullable = false)
	private LocalDateTime checkoutTime;

	@Column(nullable = false)
	private LocalDateTime startBreakTime;

	@Column(nullable = false)
	private LocalDateTime endBreakTime;
}
