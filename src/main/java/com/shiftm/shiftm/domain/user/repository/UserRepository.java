package com.shiftm.shiftm.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiftm.shiftm.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
}
