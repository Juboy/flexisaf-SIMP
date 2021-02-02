package com.flexisaf.simp.controller;

import java.util.Iterator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flexisaf.simp.SimpException;
import com.flexisaf.simp.model.Response;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Response> handleSimpException(SimpException exception) {
		Response response = Response.builder()
				.status(Response.FAILURE)
				.message(exception.getMessage())
				.build();
		return ResponseEntity
				.badRequest()
				.body(response);
	}
	
	@ExceptionHandler
	public Response handleRequestParameterValidationError(ConstraintViolationException exception) {
		
		
		StringBuilder errorMessage = new StringBuilder();
		Iterator<ConstraintViolation<?>> iterator = exception.getConstraintViolations().iterator();
		
		while (iterator.hasNext()) {
			ConstraintViolation<?> constraint = iterator.next();
			errorMessage.append(constraint.getInvalidValue().toString() + " " + constraint.getMessage());
		}
		Response response = Response.builder()
				.status(Response.FAILURE)
				.message(errorMessage.toString())
				.build();
		
		return response;
	}
	
	@ExceptionHandler
	public Response handleRequestBodyValidationError(MethodArgumentNotValidException exception) {
		
		StringBuilder errorMessage = new StringBuilder();
		BindingResult result = exception.getBindingResult();
		for(FieldError error : result.getFieldErrors()) {
			errorMessage.append(error.getField() + " " + error.getDefaultMessage() + System.lineSeparator());
		}
		Response response = Response.builder()
				.status(Response.FAILURE)
				.message(errorMessage.toString())
				.build();
		
		return response;
	}
}
