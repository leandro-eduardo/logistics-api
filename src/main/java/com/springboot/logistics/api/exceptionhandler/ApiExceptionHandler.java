package com.springboot.logistics.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.springboot.logistics.domain.service.exceptions.ClienteExistenteException;
import com.springboot.logistics.domain.service.exceptions.ClienteNaoEncontradoException;

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
@ControllerAdvice // essa anotação diz que a classe é um componente do Spring porém com um
				  // propósito específico de tratar exceções de forma global, ou seja, para todos
				  // os controladores da aplicação
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<DetalhesErro.Campo> campos = new ArrayList<>();

		for (ObjectError error : exception.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			campos.add(new DetalhesErro.Campo(nome, mensagem));
		}

		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(status.value());
		erro.setDataHora(LocalDateTime.now());
		erro.setTitulo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.");
		erro.setCampos(campos);

		return handleExceptionInternal(exception, erro, headers, status, request);
	}
	
	@ExceptionHandler(ClienteExistenteException.class)
	public ResponseEntity<Object> handleClienteExistenteException(ClienteExistenteException exception, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;		
		
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(status.value());
		erro.setDataHora(LocalDateTime.now());
		erro.setTitulo(exception.getMessage());
		
		return handleExceptionInternal(exception, erro, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(ClienteNaoEncontradoException.class)
	public ResponseEntity<Object> handleClienteNaoEncontradoException(ClienteNaoEncontradoException exception, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;		
		
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(status.value());
		erro.setDataHora(LocalDateTime.now());
		erro.setTitulo(exception.getMessage());
		
		return handleExceptionInternal(exception, erro, new HttpHeaders(), status, request);
	}

}