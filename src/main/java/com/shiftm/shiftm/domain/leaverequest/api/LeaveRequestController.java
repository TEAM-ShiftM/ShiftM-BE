package com.shiftm.shiftm.domain.leaverequest.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.leaverequest.domain.LeaveRequest;
import com.shiftm.shiftm.domain.leaverequest.dto.request.RequestLeaveRequest;
import com.shiftm.shiftm.domain.leaverequest.dto.response.LeaveRequestResponse;
import com.shiftm.shiftm.domain.leaverequest.service.LeaveRequestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/leave-request")
@RestController
public class LeaveRequestController {
	private final LeaveRequestService leaveRequestService;

	@PostMapping()
	public LeaveRequestResponse requestLeave(@RequestParam String userId, @RequestBody RequestLeaveRequest requestDto) {
		LeaveRequest leaveRequest = leaveRequestService.requestLeave(userId, requestDto);
		return new LeaveRequestResponse(leaveRequest);
	}
}
