package com.shiftm.shiftm.test.setup.domain;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shiftm.shiftm.domain.member.dao.MemberRepository;
import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.domain.enums.Gender;
import com.shiftm.shiftm.domain.member.domain.enums.Role;
import com.shiftm.shiftm.domain.member.domain.enums.Status;

@Component
public class MemberSetUp {
	@Autowired
	private MemberRepository memberRepository;

	public Member build() {
		return Member.builder()
			.id("shiftm")
			.password("Password!0")
			.email("endoxff@gmail.com")
			.name("시프트엠")
			.birthDate(LocalDate.of(2000, 1, 1))
			.gender(Gender.FEMALE)
			.status(Status.ACTIVE)
			.role(Role.USER)
			.build();
	}

	public Member build(final String id, final String email) {
		return Member.builder()
			.id(id)
			.password("Password!0")
			.email(email)
			.name("시프트엠")
			.birthDate(LocalDate.of(2000, 1, 1))
			.gender(Gender.FEMALE)
			.status(Status.ACTIVE)
			.role(Role.USER)
			.build();
	}

	public Member save() {
		final Member member = build();
		return memberRepository.save(member);
	}
}
