package com.shiftm.shiftm.domain.leave.domain;

import java.time.LocalDate;

import com.shiftm.shiftm.domain.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "leaves")
@Entity
public class Leave {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(nullable = false)
	private LocalDate expirationDate;

	@Setter
	@Column(nullable = false)
	private Integer count;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Leave(LocalDate expirationDate, Integer count, User user) {
		this.expirationDate = expirationDate;
		this.count = count;
		this.user = user;
	}
}
