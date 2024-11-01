package com.shiftm.shiftm.domain.member.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.dto.request.EmailCodeVerificationRequest;
import com.shiftm.shiftm.domain.member.dto.request.EmailVerificationRequest;
import com.shiftm.shiftm.domain.member.dto.request.FindIdRequest;
import com.shiftm.shiftm.domain.member.dto.request.FindPasswordRequest;
import com.shiftm.shiftm.domain.member.dto.request.IdValidationRequest;
import com.shiftm.shiftm.domain.member.dto.request.SignUpRequest;
import com.shiftm.shiftm.domain.member.dto.request.UpdateProfileRequest;
import com.shiftm.shiftm.domain.member.dto.response.EmailCodeVerificationResponse;
import com.shiftm.shiftm.domain.member.dto.response.IdValidationResponse;
import com.shiftm.shiftm.domain.member.dto.response.MemberResponse;
import com.shiftm.shiftm.domain.member.service.EmailService;
import com.shiftm.shiftm.domain.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
	private final MemberService memberService;
	private final EmailService emailService;

	@PostMapping("/signup")
	public MemberResponse signUp(@Valid @RequestBody final SignUpRequest requestDto) {
		return memberService.signUp(requestDto);
	}

	@PostMapping("/validation/id")
	public IdValidationResponse isIdDuplicated(@RequestBody IdValidationRequest requestDto) {
		boolean isIdDuplicated = true; // userService.isIdDuplicated(requestDto.id());
		return new IdValidationResponse(isIdDuplicated);
	}

	@PostMapping("/verification/email")
	public void sendEmailVerificationCode(@RequestBody EmailVerificationRequest requestDto) {
		emailService.sendEmailVerificationCode(requestDto.email());
	}

	@PostMapping("/verification/email/code")
	public EmailCodeVerificationResponse verifyEmailCode(@RequestBody EmailCodeVerificationRequest requestDto) {
		boolean isVerifiedEmailCode = emailService.verifyEmailCode(requestDto.email(), requestDto.verificationCode());
		return new EmailCodeVerificationResponse(isVerifiedEmailCode);
	}

	@PostMapping("/find/id")
	public void findId(@RequestBody FindIdRequest requestDto) {
		emailService.findId(requestDto.email());
	}

	@PostMapping("/find/password")
	public void findPassword(@RequestBody FindPasswordRequest requestDto) {
		emailService.findPassword(requestDto.id(), requestDto.email());
	}

	/* 하드 코딩 - userId 수정 필요 */
	@GetMapping("/me")
	public MemberResponse getProfile(@RequestParam String userId) {
		Member user = memberService.getProfile(userId);
		return new MemberResponse(user);
	}

	/* 하드 코딩 - userId 수정 필요 */
	@PatchMapping("/me")
	public MemberResponse updateProfile(@RequestParam String userId, @Valid @RequestBody UpdateProfileRequest requestDto) {
		Member user = memberService.updateProfile(userId, requestDto);
		return new MemberResponse(user);
	}

	/* 하드 코딩 - userId 수정 필요 */
	@DeleteMapping("/me")
	public String withdraw(@RequestParam String userId) {
		memberService.withdraw(userId);
		return "회원 탈퇴 완료";
	}
}
