package com.shiftm.shiftm.global.error;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.shiftm.shiftm.global.error.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private String code;
	private String message;
	private List<FieldError> errors;

	private ErrorResponse(final ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.errors = new ArrayList<>();
	}

	private ErrorResponse(final ErrorCode errorCode, final List<FieldError> errors) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.errors = errors;
	}

	public static ErrorResponse of(final ErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}

	public static ErrorResponse of(final ErrorCode errorCode, BindingResult bindingResult) {
		return new ErrorResponse(errorCode, FieldError.of(bindingResult));
	}

	public static ErrorResponse of(final ErrorCode errorCode, final List<FieldError> errors) {
		return new ErrorResponse(errorCode, errors);
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FieldError {
		private String field;
		private String value;
		private String reason;

		private FieldError(final String field, final String value, final String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}

		private static List<FieldError> of(final BindingResult bindingResult) {
			final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
			return fieldErrors.stream()
				.map(error -> new FieldError(
					error.getField(),
					error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
					error.getDefaultMessage()))
				.collect(Collectors.toList());
		}

		private static List<FieldError> of(final String field, final String value, final String reason) {
			List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, reason));
			return fieldErrors;
		}
	}
}
