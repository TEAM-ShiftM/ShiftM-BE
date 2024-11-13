package com.shiftm.shiftm.domain.shift.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shiftm.shiftm.domain.shift.domain.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

	@Query("SELECT s FROM Shift s WHERE s.member.id = :userId ORDER BY s.id DESC")
	Optional<Shift> findLatestShiftByUserId(@Param("userId") String userId);

	@Query("SELECT s FROM Shift s WHERE s.member.id = :userId AND s.checkIn.checkInTime BETWEEN :startOfWeek AND :endOfWeek ORDER BY s.checkIn.checkInTime ASC")
	List<Shift> findWeeklyShiftsByUserId(@Param("userId") String userId,
										 @Param("startOfWeek") LocalDateTime startOfWeek,
										 @Param("endOfWeek") LocalDateTime endOfWeek);
}
