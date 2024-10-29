package com.shiftm.shiftm.domain.leave.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.leave.domain.Leave;
import com.shiftm.shiftm.domain.leave.dto.request.CreateLeaveRequest;
import com.shiftm.shiftm.domain.leave.repository.LeaveRepository;
import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ManagerLeaveService {
	private final LeaveRepository leaveRepository;
	private final UserService userService;

	@Transactional
	public Leave createLeave(CreateLeaveRequest requestDto) {
		User user = userService.getUser(requestDto.userId());
		Leave leave = createLeaveObj(requestDto.expirationDate(), requestDto.count(), user);

		return leaveRepository.save(leave);
	}

	private Leave createLeaveObj(LocalDate expirationDate, int count, User user) {
		return Leave.builder()
			.expirationDate(expirationDate)
			.count(count)
			.user(user)
			.build();
	}
}
