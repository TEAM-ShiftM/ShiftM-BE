package com.shiftm.shiftm.domain.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.dto.request.SignUpRequest;
import com.shiftm.shiftm.domain.user.dto.response.UserResponse;
import com.shiftm.shiftm.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public UserResponse signUp(@Valid @RequestBody SignUpRequest requestDto) {
		User user = userService.signUp(requestDto);
		return new UserResponse(user);
	}

	/* 하드 코딩 - userId 수정 필요 */
	@GetMapping("/me")
	public UserResponse getProfile(@RequestParam String userId) {
		User user = userService.getProfile(userId);
		return new UserResponse(user);
	}
}
