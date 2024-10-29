package com.shiftm.shiftm.domain.shift.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shiftm.shiftm.domain.shift.domain.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

	@Query("SELECT s FROM Shift s WHERE s.user.id = :userId ORDER BY s.id DESC")
	Optional<Shift> findLatestShiftByUserId(@Param("userId") String userId);
}
