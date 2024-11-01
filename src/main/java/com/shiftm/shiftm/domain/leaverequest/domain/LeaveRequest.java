package com.shiftm.shiftm.domain.leaverequest.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.shiftm.shiftm.domain.leave.domain.Leave;
import com.shiftm.shiftm.domain.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class LeaveRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Setter
	@Column(nullable = false)
	private Boolean approval;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Member user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leaves_id")
	private Leave leave;

	@Builder
	public LeaveRequest(LocalDate startDate, LocalDate endDate, Boolean approval,
		LocalDateTime createdAt, Member user, Leave leave) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.approval = approval;
		this.createdAt = createdAt;
		this.user = user;
		this.leave = leave;
	}
}
