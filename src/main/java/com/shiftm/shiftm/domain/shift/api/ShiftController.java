package com.shiftm.shiftm.domain.shift.api;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.shift.domain.Shift;
import com.shiftm.shiftm.domain.shift.dto.response.CheckInResponse;
import com.shiftm.shiftm.domain.shift.dto.response.CheckOutResponse;
import com.shiftm.shiftm.domain.shift.service.ShiftService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/shift")
@RestController
public class ShiftController {
	private final ShiftService shiftService;

	@PostMapping("/check-in")
	public CheckInResponse recordCheckIn(@RequestParam String userId) {
		Shift shift = shiftService.recordCheckIn(userId);
		return new CheckInResponse(shift);
	}

	@PatchMapping("/check-out")
	public CheckOutResponse recordCheckOut(@RequestParam String userId) {
		Shift shift = shiftService.recordCheckOut(userId);
		return new CheckOutResponse(shift);
	}
}
