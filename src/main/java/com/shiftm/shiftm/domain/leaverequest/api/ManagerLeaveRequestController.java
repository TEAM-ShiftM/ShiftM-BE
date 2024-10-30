package com.shiftm.shiftm.domain.leaverequest.api;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.leaverequest.domain.LeaveRequest;
import com.shiftm.shiftm.domain.leaverequest.dto.response.LeaveRequestResponse;
import com.shiftm.shiftm.domain.leaverequest.service.ManagerLeaveRequestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerLeaveRequestController {
	private final ManagerLeaveRequestService managerLeaveRequestService;

	@PatchMapping("/leave-request/{id}")
	public LeaveRequestResponse approveLeaveRequest(@PathVariable Long id) {
		LeaveRequest leaveRequest = managerLeaveRequestService.approveLeaveRequest(id);
		return new LeaveRequestResponse(leaveRequest);
	}
}
