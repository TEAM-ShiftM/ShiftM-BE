package com.shiftm.shiftm.domain.leaverequest.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.leaverequest.domain.LeaveRequest;
import com.shiftm.shiftm.domain.leaverequest.exception.LeaveRequestNotFoundException;
import com.shiftm.shiftm.domain.leaverequest.repository.LeaveRequestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ManagerLeaveRequestService {
	private final LeaveRequestRepository leaveRequestRepository;

	@Transactional
	public LeaveRequest approveLeaveRequest(Long id) {
		LeaveRequest leaveRequest = getLeaveRequest(id);

		leaveRequest.setApproval(true);

		return leaveRequest;
	}

	private LeaveRequest getLeaveRequest(Long id) {
		Optional<LeaveRequest> optionalLeaveRequest = leaveRequestRepository.findById(id);

		if (optionalLeaveRequest.isEmpty()) {
			throw new LeaveRequestNotFoundException();
		}

		return optionalLeaveRequest.get();
	}
}
