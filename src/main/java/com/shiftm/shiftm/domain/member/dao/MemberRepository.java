package com.shiftm.shiftm.domain.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiftm.shiftm.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);
}
