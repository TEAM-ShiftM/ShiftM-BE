package com.shiftm.shiftm.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
	/* COMMON ERROR */
	INTERNAL_SERVER_ERROR(500, "COMMON001", "Internal Server Error"),
	INVALID_INPUT_VALUE(400, "COMMON002", "Invalid Input Value"),
	ENTITY_NOT_FOUND(400, "COMMON003", "Entity Not Found"),

	/* MEMBER ERROR */
	ID_DUPLICATE(400, "MEM001", "It Is Duplicate ID"),
	EMAIL_DUPLICATE(400, "MEM002", "It Is Duplicate Email"),

	/* AUTH ERROR */
	USER_NOT_FOUND(400, "AUTH001", "User Not Found"),
	INVALID_PASSWORD(400, "AUTH002", "It Is Invalid Password"),
	INVALID_BEARER_PREFIX(400, "AUTH003", "It Is Invalid Bearer Prefix"),
	INVALID_TOKEN(400, "AUTH004", "It Is Invalid Token"),
	REFRESH_TOKEN_NOT_FOUND(400, "AUTH005", "Refresh Token Not Found"),
	INVALID_REFRESH_TOKEN(400, "AUTH006", "It Is Invalid Refresh Token"),

	/* SHIFT ERROR */
	SHIFT_NOT_FOUND(400, "SHIFT001", "Shift Not Found"),

	/* LEAVE ERROR */
	LEAVE_NOT_FOUND(400, "LEAVE001", "Leave Not Found"),
	INVALID_LEAVE_DAYS(400, "LEAVE002", "It Is Invalid Leave Days"),

	/* LEAVE REQUEST ERROR */
	LEAVE_REQUEST_NOT_FOUND(400, "LR001", "Leave Request Not Found"),

	/* EMAIL ERROR */
	UNABLE_TO_SEND_EMAIL(500, "EMAIL001", "Unable To Send Email");

	private final int status;
	private final String code;
	private final String message;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
