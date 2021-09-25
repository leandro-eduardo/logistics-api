package com.springboot.logistics.domain.service.exceptions;

public class RegisteredCityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegisteredCityException(String message) {
		super(message);
	}

}