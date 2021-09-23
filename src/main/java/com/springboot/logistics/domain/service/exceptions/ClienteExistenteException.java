package com.springboot.logistics.domain.service.exceptions;

public class ClienteExistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClienteExistenteException(String message) {
		super(message);
	}
	
}
