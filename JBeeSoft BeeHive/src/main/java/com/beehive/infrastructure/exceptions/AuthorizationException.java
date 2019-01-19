package com.beehive.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends RuntimeException{

	private static final long serialVersionUID = 9140619007576741162L;
	private static final String MESSAGE = "You don't have required permissions to perform this action";
	
	public AuthorizationException() {
		super(MESSAGE);
	}
	
}
