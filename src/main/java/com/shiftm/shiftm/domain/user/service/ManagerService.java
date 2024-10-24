package com.shiftm.shiftm.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ManagerService {
	private final UserRepository userRepository;

	public List<User> getEmployeeList() {
		return userRepository.findAll();
	}
}
