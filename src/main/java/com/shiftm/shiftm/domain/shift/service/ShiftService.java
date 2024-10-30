package com.shiftm.shiftm.domain.shift.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
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

	public long getShiftTimeOfWeek(String userId) {
		List<Shift> weeklyShift = getShiftOfWeek(userId);

		long weeklyShiftTime = 0;

		for (Shift shift: weeklyShift) {
			if (shift.getCheckin() != null && shift.getCheckout() != null) {
				LocalDateTime checkInTime = shift.getCheckin().getCheckinTime();
				LocalDateTime checkOutTime = shift.getCheckout().getCheckoutTime();

				long minutesWorked = Duration.between(checkInTime, checkOutTime).toMinutes();
				weeklyShiftTime += minutesWorked;
			}
		}

		return weeklyShiftTime;
	}

	private List<Shift> getShiftOfWeek(String userId) {
		LocalDateTime startOfWeek = getStartOfWeek();
		LocalDateTime endOfWeek = getEndOfWeek();

		return shiftRepository.findWeeklyShiftsByUserId(userId, startOfWeek, endOfWeek);
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

	private LocalDateTime getStartOfWeek() {
		return LocalDateTime.now()
			.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
			.withHour(0).withMinute(0).withSecond(0).withNano(0);
	}

	private LocalDateTime getEndOfWeek() {
		return LocalDateTime.now()
			.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
			.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
	}
}
