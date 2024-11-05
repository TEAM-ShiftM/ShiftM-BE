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
import com.shiftm.shiftm.domain.member.dto.request.VerifyEmailCodeRequest;
import com.shiftm.shiftm.domain.member.dto.request.FindPasswordRequest;
import com.shiftm.shiftm.domain.member.dto.request.SignUpRequest;
import com.shiftm.shiftm.domain.member.dto.request.UpdateProfileRequest;
import com.shiftm.shiftm.domain.member.dto.response.CheckResponse;
import com.shiftm.shiftm.domain.member.dto.response.MemberResponse;
import com.shiftm.shiftm.domain.member.service.EmailService;
import com.shiftm.shiftm.domain.member.service.MemberService;
import com.shiftm.shiftm.domain.member.service.MemberSignUpService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
	private final MemberSignUpService memberSignUpService;
	private final MemberService memberService;
	private final EmailService emailService;

	@PostMapping("/signup")
	public MemberResponse signUp(@Valid @RequestBody final SignUpRequest requestDto) {
		return memberSignUpService.signUp(requestDto);
	}

	@GetMapping("/check/id")
	public CheckResponse checkUniqueId(@RequestParam final String id) {
		return memberSignUpService.checkUniqueId(id);
	}

	@PostMapping("/check/email")
	public void sendEmailVerificationCode(@RequestParam final String email) {
		memberSignUpService.sendEmailVerificationCode(email);
	}

	@PostMapping("/check/email/code")
	public CheckResponse verifyEmailCode(@Valid @RequestBody final VerifyEmailCodeRequest requestDto) {
		return memberSignUpService.verifyEmailCode(requestDto.email(), requestDto.verificationCode());
	}

	@PostMapping("/find/id")
	public void findId(@RequestParam final String email) {
		memberService.findId(email);
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
