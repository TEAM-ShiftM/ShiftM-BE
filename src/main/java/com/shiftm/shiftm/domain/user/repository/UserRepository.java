package com.shiftm.shiftm.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiftm.shiftm.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
	boolean existsByEmail(String email);
}
