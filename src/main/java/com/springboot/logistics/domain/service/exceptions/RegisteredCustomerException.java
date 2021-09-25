package com.springboot.logistics.domain.service.exceptions;

public class RegisteredCustomerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegisteredCustomerException(String message) {
		super(message);
	}

}