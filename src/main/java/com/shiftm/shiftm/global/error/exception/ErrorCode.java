package com.shiftm.shiftm.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
	/* COMMON ERROR */
	INTERNAL_SERVER_ERROR(500, "COMMON001", "Internal Server Error"),
	INVALID_INPUT_VALUE(400, "COMMON002", "Invalid Input Value"),

	/* USER ERROR */
	ID_DUPLICATE(400, "USER001", "It Is Duplicate ID"),
	EMAIL_DUPLICATE(400, "USER002", "It Is Duplicate Email");

	private final int status;
	private final String code;
	private final String message;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
