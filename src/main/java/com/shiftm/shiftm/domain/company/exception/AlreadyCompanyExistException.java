package com.shiftm.shiftm.domain.company.exception;

import com.shiftm.shiftm.global.error.exception.BusinessException;
import com.shiftm.shiftm.global.error.exception.ErrorCode;

public class AlreadyCompanyExistException extends BusinessException {
	public AlreadyCompanyExistException() {
		super(ErrorCode.ALREADY_COMPANY_EXIST.getMessage(), ErrorCode.ALREADY_COMPANY_EXIST);
	}
}
