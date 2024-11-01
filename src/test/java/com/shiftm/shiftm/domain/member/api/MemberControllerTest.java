package com.shiftm.shiftm.domain.member.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.dto.request.SignUpRequest;
import com.shiftm.shiftm.test.ControllerTest;
import com.shiftm.shiftm.test.setup.request.SignUpRequestBuilder;

class MemberControllerTest extends ControllerTest {
	@Test
	public void 회원_가입_성공() throws Exception {
		// given
		final Member member = memberSetUp.build();
		final SignUpRequest requestDto = SignUpRequestBuilder.build(member);

		// when
		final ResultActions resultActions = requestSignUp(requestDto);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(member.getId()))
			.andExpect(jsonPath("email").value(member.getEmail()))
			.andExpect(jsonPath("name").value(member.getName()))
			.andExpect(jsonPath("birthDate").value(member.getBirthDate().toString()))
			.andExpect(jsonPath("gender").value(member.getGender().toString()));
	}

	@Test
	public void 회원_가입_실패_아이디_중복() throws Exception {
		// given
		final Member existedMember = memberSetUp.save();
		final SignUpRequest requestDto = SignUpRequestBuilder.build(existedMember);

		// when
		final ResultActions resultActions = requestSignUp(requestDto);

		// then
		resultActions
			.andExpect(status().isBadRequest());
	}

	@Test
	public void 회원_가입_실패_이메일_중복() throws Exception {
		// given
		final Member existedMember = memberSetUp.save();
		final Member newMember = memberSetUp.build("shiftmmm", "endoxff@gmail.com");
		final SignUpRequest requestDto = SignUpRequestBuilder.build(newMember);

		// when
		final ResultActions resultActions = requestSignUp(requestDto);

		// then
		resultActions
			.andExpect(status().isBadRequest());
	}

	private ResultActions requestSignUp(SignUpRequest requestDto) throws Exception {
		return mockMvc.perform(post("/member/signup")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(requestDto)));
	}
}
