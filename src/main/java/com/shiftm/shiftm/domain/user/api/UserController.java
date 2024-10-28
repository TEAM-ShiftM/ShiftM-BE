package com.shiftm.shiftm.domain.user.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.dto.request.EmailVerificationRequest;
import com.shiftm.shiftm.domain.user.dto.request.IdValidationRequest;
import com.shiftm.shiftm.domain.user.dto.request.SignUpRequest;
import com.shiftm.shiftm.domain.user.dto.request.UpdateProfileRequest;
import com.shiftm.shiftm.domain.user.dto.response.IdValidationResponse;
import com.shiftm.shiftm.domain.user.dto.response.UserResponse;
import com.shiftm.shiftm.domain.user.service.EmailService;
import com.shiftm.shiftm.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
	private final UserService userService;
	private final EmailService emailService;

	@PostMapping("/signup")
	public UserResponse signUp(@Valid @RequestBody SignUpRequest requestDto) {
		User user = userService.signUp(requestDto);
		return new UserResponse(user);
	}

	@PostMapping("/validation/id")
	public IdValidationResponse isIdDuplicated(@RequestBody IdValidationRequest requestDto) {
		boolean isIdDuplicated = userService.isIdDuplicated(requestDto.id());
		return new IdValidationResponse(isIdDuplicated);
	}

	@PostMapping("/verification/email")
	public void sendEmailVerificationCode(@RequestBody EmailVerificationRequest requestDto) {
		emailService.sendEmailVerificationCode(requestDto.email());
	}

	/* 하드 코딩 - userId 수정 필요 */
	@GetMapping("/me")
	public UserResponse getProfile(@RequestParam String userId) {
		User user = userService.getProfile(userId);
		return new UserResponse(user);
	}

	/* 하드 코딩 - userId 수정 필요 */
	@PatchMapping("/me")
	public UserResponse updateProfile(@RequestParam String userId, @Valid @RequestBody UpdateProfileRequest requestDto) {
		User user = userService.updateProfile(userId, requestDto);
		return new UserResponse(user);
	}

	/* 하드 코딩 - userId 수정 필요 */
	@DeleteMapping("/me")
	public String withdraw(@RequestParam String userId) {
		userService.withdraw(userId);
		return "회원 탈퇴 완료";
	}
}
