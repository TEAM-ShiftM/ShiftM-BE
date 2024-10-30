package com.shiftm.shiftm.domain.leave.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.leave.domain.Leave;
import com.shiftm.shiftm.domain.leave.dto.request.CreateLeaveRequest;
import com.shiftm.shiftm.domain.leave.dto.request.UpdateLeaveRequest;
import com.shiftm.shiftm.domain.leave.exception.LeaveNotFoundException;
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

	@Transactional
	public Leave updateLeave(Long id, UpdateLeaveRequest requestDto) {
		Leave leave = getLeave(id);

		leave.setExpirationDate(requestDto.expirationDate());
		leave.setCount(requestDto.count());

		return leave;
	}

	private Leave createLeaveObj(LocalDate expirationDate, int count, User user) {
		return Leave.builder()
			.expirationDate(expirationDate)
			.count(count)
			.user(user)
			.build();
	}

	private Leave getLeave(Long id) {
		Optional<Leave> optionalLeave = leaveRepository.findById(id);

		if (optionalLeave.isEmpty()) {
			throw new LeaveNotFoundException();
		}

		return optionalLeave.get();
	}
}
