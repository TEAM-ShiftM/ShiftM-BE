package com.shiftm.shiftm.infra.email;

import com.shiftm.shiftm.global.error.exception.BusinessException;
import com.shiftm.shiftm.global.error.exception.ErrorCode;

public class UnableToSendEmailException extends BusinessException {
	public UnableToSendEmailException() {
		super(ErrorCode.UNABLE_TO_SEND_EMAIL);
	}
}
