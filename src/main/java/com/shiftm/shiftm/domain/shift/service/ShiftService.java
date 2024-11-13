package com.shiftm.shiftm.domain.shift.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.company.dao.CompanyFinder;
import com.shiftm.shiftm.domain.member.dao.MemberFinder;
import com.shiftm.shiftm.domain.shift.domain.CheckIn;
import com.shiftm.shiftm.domain.shift.domain.CheckOut;
import com.shiftm.shiftm.domain.shift.domain.Shift;
import com.shiftm.shiftm.domain.shift.domain.enums.Status;
import com.shiftm.shiftm.domain.shift.dto.response.CheckInResponse;
import com.shiftm.shiftm.domain.shift.exception.ShiftNotFoundException;
import com.shiftm.shiftm.domain.shift.dao.ShiftRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShiftService {
	private final ShiftRepository shiftRepository;
	private final MemberFinder memberFinder;
	private final CompanyFinder companyFinder;

	@Transactional
	public CheckInResponse checkIn(String userId) {
		CheckIn checkIn = createCheckIn();

		Shift shift = shiftRepository.save(createShift(checkIn, userId));

		return new CheckInResponse(shift);
	}

	private CheckIn createCheckIn() {
		LocalDateTime checkInTime = LocalDateTime.now();
		Status status = getCheckInStatus(checkInTime.toLocalTime());

		return CheckIn.builder()
			.checkInTime(checkInTime)
			.checkInStatus(status)
			.build();
	}

	private Shift createShift(CheckIn checkin, String userId) {
		return Shift.builder()
			.checkIn(checkin)
			.member(memberFinder.getUser(userId))
			.build();
	}

	private Status getCheckInStatus(LocalTime checkInTime) {
		LocalTime defaultCheckInTime = companyFinder.getCheckInTime();

		if (checkInTime.isAfter(defaultCheckInTime.plusMinutes(5))) {
			return Status.LATE_CHECK_IN;
		}

		return Status.ON_TIME;
	}

	@Transactional
	public Shift recordCheckOut(String userId) {
		Shift shift = getLatestShift(userId);
		CheckOut checkout = createCheckOut();

		shift.setCheckOut(checkout);

		return shift;
	}

	public long getShiftTimeOfWeek(String userId) {
		List<Shift> weeklyShift = getShiftOfWeek(userId);

		long weeklyShiftTime = 0;

		for (Shift shift: weeklyShift) {
			if (shift.getCheckIn() != null && shift.getCheckOut() != null) {
				LocalDateTime checkInTime = shift.getCheckIn().getCheckInTime();
				LocalDateTime checkOutTime = shift.getCheckOut().getCheckOutTime();

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

	private CheckOut createCheckOut() {
		return CheckOut.builder()
			.checkOutTime(LocalDateTime.now())
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
