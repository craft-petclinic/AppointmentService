package com.demo.petclinic.appointment.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationFailedException extends RuntimeException {

	public ValidationFailedException(String exception) {
		super(exception);
	}

}
