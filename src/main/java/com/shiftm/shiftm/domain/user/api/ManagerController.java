package com.shiftm.shiftm.domain.user.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.dto.response.UserResponse;
import com.shiftm.shiftm.domain.user.service.ManagerService;
import com.shiftm.shiftm.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerController {
	private final UserService userService;
	private final ManagerService managerService;

	/* 하드 코딩 - userId 수정 필요 */
	@GetMapping("/user")
	public List<UserResponse> getEmployeeList() {
		List<User> userList = managerService.getEmployeeList();

		return userList.stream()
			.map(UserResponse::new)
			.collect(Collectors.toList());
	}

	@GetMapping("/user/{id}")
	public UserResponse getEmployee(@PathVariable String id) {
		User user = userService.getProfile(id);
		return new UserResponse(user);
	}
}
