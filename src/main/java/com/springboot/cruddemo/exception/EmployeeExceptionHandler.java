package com.springboot.cruddemo.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springboot.cruddemo.errormessage.ErrorResponse;

@RestControllerAdvice
public class EmployeeExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(EmployeeNotFoundException e) {
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.NOT_FOUND.value());
		error.setMessage(e.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e) {
		List<String> errors = e.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
		error.setMessage(errors.toString());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);

	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(e.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);

	}

}
