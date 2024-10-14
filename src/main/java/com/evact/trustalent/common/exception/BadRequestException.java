package com.evact.trustalent.common.exception;

import java.io.Serial;

public class BadRequestException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -8257423359407774563L;

	public BadRequestException(Exception e) {
		super(e);
	}

	public BadRequestException(String message) {
		super(message);
	}

}
