package com.shiftm.shiftm.domain.member.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.dto.request.UpdateProfileRequest;
import com.shiftm.shiftm.domain.member.dto.response.MemberResponse;
import com.shiftm.shiftm.domain.member.service.ManagerService;
import com.shiftm.shiftm.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerController {
	private final MemberService userService;
	private final ManagerService managerService;

	/* 하드 코딩 - userId 수정 필요 */
	@GetMapping("/user")
	public List<MemberResponse> getEmployeeList() {
		List<Member> userList = managerService.getEmployeeList();

		return userList.stream()
			.map(MemberResponse::new)
			.collect(Collectors.toList());
	}

	@GetMapping("/user/{id}")
	public MemberResponse getEmployee(@PathVariable String id) {
		Member user = userService.getProfile(id);
		return new MemberResponse(user);
	}

	@PatchMapping("/user/{id}")
	public MemberResponse updateEmployee(@PathVariable String id, @RequestBody UpdateProfileRequest requestDto) {
		Member user = userService.updateProfile(id, requestDto);
		return new MemberResponse(user);
	}

	@DeleteMapping("/user/{id}")
	public String deleteEmployee(@PathVariable String id) {
		userService.withdraw(id);
		return "회원 탈퇴 완료";
	}
}
