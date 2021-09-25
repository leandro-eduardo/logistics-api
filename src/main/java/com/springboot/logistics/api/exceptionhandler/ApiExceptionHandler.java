package com.springboot.logistics.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.springboot.logistics.domain.service.exceptions.CityNotFoundException;
import com.springboot.logistics.domain.service.exceptions.CustomerNotFoundException;
import com.springboot.logistics.domain.service.exceptions.RegisteredCityException;
import com.springboot.logistics.domain.service.exceptions.RegisteredCustomerException;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<ErrorDetails.Field> fields = new ArrayList<>();

		for (ObjectError error : exception.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			fields.add(new ErrorDetails.Field(nome, mensagem));
		}

		ErrorDetails error = new ErrorDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(status.value());
		error.setError(status.getReasonPhrase());
		error.setMessage("one or more fields are invalid. fill in correctly and try again.");
		error.setFields(fields);

		return handleExceptionInternal(exception, error, headers, status, request);
	}

	@ExceptionHandler(RegisteredCustomerException.class)
	public ResponseEntity<Object> handleRegisteredCustomerException(RegisteredCustomerException exception,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		ErrorDetails error = new ErrorDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(status.value());
		error.setError(status.getReasonPhrase());
		error.setMessage(exception.getMessage());
		error.setDeveloperMessage("http://error.logistics.com/400");
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException exception,
			WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		ErrorDetails error = new ErrorDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(status.value());
		error.setError(status.getReasonPhrase());
		error.setMessage(exception.getMessage());
		error.setDeveloperMessage("http://error.logistics.com/404");

		return handleExceptionInternal(exception, error, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(RegisteredCityException.class)
	public ResponseEntity<Object> handleRegisteredCityException(RegisteredCityException exception,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		ErrorDetails error = new ErrorDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(status.value());
		error.setError(status.getReasonPhrase());
		error.setMessage(exception.getMessage());
		error.setDeveloperMessage("http://error.logistics.com/400");
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(CityNotFoundException.class)
	public ResponseEntity<Object> handleCityNotFoundException(CityNotFoundException exception, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		ErrorDetails error = new ErrorDetails();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(status.value());
		error.setError(status.getReasonPhrase());
		error.setMessage(exception.getMessage());
		error.setDeveloperMessage("http://error.logistics.com/404");

		return handleExceptionInternal(exception, error, new HttpHeaders(), status, request);
	}

}