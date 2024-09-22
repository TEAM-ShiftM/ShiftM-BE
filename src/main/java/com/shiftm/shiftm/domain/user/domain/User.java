package com.shiftm.shiftm.domain.user.domain;

import java.time.LocalDate;

import com.shiftm.shiftm.domain.user.domain.enums.Gender;
import com.shiftm.shiftm.domain.user.domain.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
	@Id
	private String id;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private LocalDate birthDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column
	private LocalDate entryDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
}
