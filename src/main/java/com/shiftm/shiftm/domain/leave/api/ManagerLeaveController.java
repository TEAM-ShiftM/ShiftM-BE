package com.shiftm.shiftm.domain.leave.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.leave.domain.Leave;
import com.shiftm.shiftm.domain.leave.dto.request.CreateLeaveRequest;
import com.shiftm.shiftm.domain.leave.dto.response.LeaveResponse;
import com.shiftm.shiftm.domain.leave.service.ManagerLeaveService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerLeaveController {
	private final ManagerLeaveService managerLeaveService;

	@PostMapping("/leave")
	public LeaveResponse createLeave(@RequestBody CreateLeaveRequest requestDto) {
		Leave leave = managerLeaveService.createLeave(requestDto);
		return new LeaveResponse(leave);
	}
}
