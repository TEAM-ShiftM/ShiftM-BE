package com.shiftm.shiftm.domain.shift.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiftm.shiftm.domain.shift.domain.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
