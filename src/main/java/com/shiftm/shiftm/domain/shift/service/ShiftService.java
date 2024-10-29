package com.shiftm.shiftm.domain.shift.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.shift.domain.Checkin;
import com.shiftm.shiftm.domain.shift.domain.Checkout;
import com.shiftm.shiftm.domain.shift.domain.Shift;
import com.shiftm.shiftm.domain.shift.exception.ShiftNotFoundException;
import com.shiftm.shiftm.domain.shift.repository.ShiftRepository;
import com.shiftm.shiftm.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShiftService {
	private final ShiftRepository shiftRepository;
	private final UserService userService;

	@Transactional
	public Shift recordCheckIn(String userId) {
		Checkin checkin = createCheckIn();
		Shift shift = createShift(checkin, userId);

		return shiftRepository.save(shift);
	}

	@Transactional
	public Shift recordCheckOut(String userId) {
		Shift shift = getLatestShift(userId);
		Checkout checkout = createCheckOut();

		shift.setCheckout(checkout);

		return shift;
	}

	private Shift createShift(Checkin checkin, String userId) {
		return Shift.builder()
			.checkin(checkin)
			.user(userService.getUser(userId))
			.build();
	}

	private Checkin createCheckIn() {
		return Checkin.builder()
			.checkinTime(LocalDateTime.now())
			.approval(true)
			.build();
	}

	private Checkout createCheckOut() {
		return Checkout.builder()
			.checkoutTime(LocalDateTime.now())
			.build();
	}

	private Shift getLatestShift(String userId) {
		Optional<Shift> optionalShift = shiftRepository.findLatestShiftByUserId(userId);

		if (optionalShift.isEmpty()) {
			throw new ShiftNotFoundException();
		}

		return optionalShift.get();
	}
}
