package com.shiftm.shiftm.domain.member.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberFinder {
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public boolean isExistedId(String id) {
		return memberRepository.existsById(id);
	}

	@Transactional(readOnly = true)
	public boolean isExistedEmail(String email) {
		return memberRepository.existsByEmail(email);
	}
}
