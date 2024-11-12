package com.shiftm.shiftm.domain.company.domain;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
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
	private String companyId;

	@Column(nullable = false)
	private LocalTime checkInTime;

	@Column(nullable = false)
	private LocalTime checkOutTime;

	@Column(nullable = false)
	private int breakTime;

	@Column
	private String companyIP;

	@Builder
	public Company(String companyId, LocalTime checkInTime, LocalTime checkOutTime,
		int breakTime, String companyIP) {
		this.companyId = companyId;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.breakTime = breakTime;
		this.companyIP = companyIP;
	}
}
