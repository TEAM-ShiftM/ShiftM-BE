package com.shiftm.shiftm.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
	/* COMMON ERROR */
	INTERNAL_SERVER_ERROR(500, "COMMON001", "Internal Server Error");

	private final int status;
	private final String code;
	private final String message;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}