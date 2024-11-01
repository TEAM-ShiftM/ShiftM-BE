package com.shiftm.shiftm.test.setup.request;

import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.dto.request.SignUpRequest;

public class SignUpRequestBuilder {
	public static SignUpRequest build(Member member) {
		return new SignUpRequest(
			member.getId(),
			member.getPassword(),
			"000000",
			member.getEmail(),
			member.getName(),
			member.getBirthDate(),
			member.getGender().toString()
		);
	}
}
