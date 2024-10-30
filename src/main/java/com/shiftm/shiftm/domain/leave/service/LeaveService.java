package com.shiftm.shiftm.domain.leave.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.leave.domain.Leave;
import com.shiftm.shiftm.domain.leave.exception.InvalidLeaveDaysException;
import com.shiftm.shiftm.domain.leave.exception.LeaveNotFoundException;
import com.shiftm.shiftm.domain.leave.repository.LeaveRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LeaveService {
	private final LeaveRepository leaveRepository;

	public Leave getLeave(Long id) {
		Optional<Leave> optionalLeave = leaveRepository.findById(id);

		if (optionalLeave.isEmpty()) {
			throw new LeaveNotFoundException();
		}

		return optionalLeave.get();
	}

	public Leave getLeave(String userId) {
		List<Leave> leaveList = leaveRepository.findByUserId(userId);

		if (leaveList.isEmpty()) {
			throw new LeaveNotFoundException();
		}

		return leaveList.get(0);
	}

	@Transactional
	public void updateLeave(Leave leave, int count) {
		if (leave.getCount() - count < 0) {
			throw new InvalidLeaveDaysException(count);
		}

		leave.setCount(leave.getCount() - count);
	}
}
