package com.shiftm.shiftm.domain.user.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.dto.response.UserResponse;
import com.shiftm.shiftm.domain.user.service.ManagerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerController {
	private final ManagerService managerService;

	/* 하드 코딩 - userId 수정 필요 */
	@GetMapping("/user")
	public List<UserResponse> getEmployeeList() {
		List<User> userList = managerService.getEmployeeList();

		return userList.stream()
			.map(UserResponse::new)
			.collect(Collectors.toList());
	}
}
