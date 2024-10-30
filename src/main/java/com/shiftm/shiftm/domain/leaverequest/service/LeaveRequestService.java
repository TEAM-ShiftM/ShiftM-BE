package com.shiftm.shiftm.domain.leaverequest.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.leave.domain.Leave;
import com.shiftm.shiftm.domain.leave.service.LeaveService;
import com.shiftm.shiftm.domain.leaverequest.domain.LeaveRequest;
import com.shiftm.shiftm.domain.leaverequest.dto.request.RequestLeaveRequest;
import com.shiftm.shiftm.domain.leaverequest.repository.LeaveRequestRepository;
import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LeaveRequestService {
	private final LeaveRequestRepository leaveRequestRepository;
	private final UserService userService;
	private final LeaveService leaveService;

	@Transactional
	public LeaveRequest requestLeave(String userId, RequestLeaveRequest requestDto) {
		User user = userService.getUser(userId);

		Leave leave = leaveService.getLeave(userId);
		int leaveDays = calculateLeaveDays(requestDto.startDate(), requestDto.endDate());
		leaveService.updateLeave(leave, leaveDays);

		LeaveRequest leaveRequest = createLeaveRequest(requestDto.startDate(), requestDto.endDate(), user, leave);

		return leaveRequestRepository.save(leaveRequest);
	}

	private LeaveRequest createLeaveRequest(LocalDate startDate, LocalDate endDate,
		User user, Leave leave) {
		return LeaveRequest.builder()
			.startDate(startDate)
			.endDate(endDate)
			.approval(false)
			.createdAt(LocalDateTime.now())
			.user(user)
			.leave(leave)
			.build();
	}

	private int calculateLeaveDays(LocalDate startDate, LocalDate endDate) {
		return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
	}
}
