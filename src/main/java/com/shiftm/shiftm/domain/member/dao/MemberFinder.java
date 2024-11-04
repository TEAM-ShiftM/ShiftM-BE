package com.shiftm.shiftm.domain.member.dao;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.member.exception.UserNotFoundException;
import com.shiftm.shiftm.domain.member.domain.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberFinder {
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public Member getUser(String userId) {
		Optional<Member> optionalUser = memberRepository.findById(userId);

		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException(userId);
		}

		return optionalUser.get();
	}

	@Transactional(readOnly = true)
	public boolean isExistedId(String id) {
		return memberRepository.existsById(id);
	}

	@Transactional(readOnly = true)
	public boolean isExistedEmail(String email) {
		return memberRepository.existsByEmail(email);
	}
}
