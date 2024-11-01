package com.shiftm.shiftm.domain.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.member.dao.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ManagerService {
	private final MemberRepository userRepository;

	public List<Member> getEmployeeList() {
		return userRepository.findAll();
	}
}
