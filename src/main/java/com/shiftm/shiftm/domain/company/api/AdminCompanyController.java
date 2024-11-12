package com.shiftm.shiftm.domain.company.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.company.dto.request.CreateCompanyRequest;
import com.shiftm.shiftm.domain.company.dto.response.CompanyResponse;
import com.shiftm.shiftm.domain.company.service.AdminCompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/admin/company")
@RestController
public class AdminCompanyController {
	private final AdminCompanyService adminCompanyService;

	@PostMapping
	public CompanyResponse createCompany(@RequestBody @Valid final CreateCompanyRequest requestDto) {
		return adminCompanyService.createCompany(requestDto);
	}

	@GetMapping
	public CompanyResponse getCompany() {
		return adminCompanyService.getCompany();
	}
}
