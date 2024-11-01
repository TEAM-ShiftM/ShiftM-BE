package com.shiftm.shiftm.domain.member.domain;

import java.time.LocalDate;

import com.shiftm.shiftm.domain.member.domain.enums.Gender;
import com.shiftm.shiftm.domain.member.domain.enums.Role;
import com.shiftm.shiftm.domain.member.domain.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
	@Id
	private String id;

	@Setter
	@Column(nullable = false)
	private String password;

	@Setter
	@Column(nullable = false, unique = true)
	private String email;

	@Setter
	@Column(nullable = false)
	private String name;

	@Setter
	@Column(nullable = false)
	private LocalDate birthDate;

	@Setter
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column
	private LocalDate entryDate;

	@Setter
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public Member(String id, String password, String email,
		String name, LocalDate birthDate, Gender gender, Status status, Role role) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
		this.status = status;
		this.role = role;
	}
}
