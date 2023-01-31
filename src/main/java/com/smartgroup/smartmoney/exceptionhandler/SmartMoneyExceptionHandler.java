package com.smartgroup.smartmoney.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SmartMoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String userMessage = this.messageSource.getMessage("invalid.message", null, Locale.getDefault());
		String devMessage = Optional.ofNullable(ex.getCause()).orElse(ex).toString();
//		String devMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		
		List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));
		
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Error> errors = createErrorList(ex.getBindingResult());
		
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleEmptyResultDataAccess(EmptyResultDataAccessException ex, 
			WebRequest request) {
		
		String userMessage = messageSource.getMessage("resource.not-found", null, Locale.getDefault());
		String devMessage = ex.toString();
		
		List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));
		
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
		String userMessage = messageSource.getMessage("resource.cannot-process-request", null, Locale.getDefault());
		String devMessage = ExceptionUtils.getRootCauseMessage(ex);
		List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Error> createErrorList(BindingResult bindingResult) {
		List<Error> errors = new ArrayList<>();
		
		for(FieldError fieldError : bindingResult.getFieldErrors()) {
			String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String devMessage = fieldError.toString();
			errors.add(new Error(userMessage, devMessage));
		}
		
		return errors;
	}
	
	public static class Error {
		
		private String userMessage;
		private String devMessage;
		
		public Error(String userMessage, String devMessage) {
			this.userMessage = userMessage;
			this.devMessage = devMessage;
		}

		public String getUserMessage() {
			return userMessage;
		}

		public void setUserMessage(String userMessage) {
			this.userMessage = userMessage;
		}

		public String getDevMessage() {
			return devMessage;
		}

		public void setDevMessage(String devMessage) {
			this.devMessage = devMessage;
		}
		
	}
	
}
