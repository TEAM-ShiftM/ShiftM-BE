package com.shiftm.shiftm.domain.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.domain.enums.Status;

public interface MemberRepository extends JpaRepository<Member, String> {
	Optional<Member> findByIdAndStatus(String id, Status status);

	Optional<Member> findByEmailAndStatus(String email, Status status);

	boolean existsByEmail(String email);
}
