package com.fullstack.api.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

	static final String UNEXPECTED_MESSAGE = "An unexpected error occurred";

	private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

	private final Environment environment;

	public ApiExceptionHandler(Environment environment) {
		this.environment = environment;
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFound(NoResourceFoundException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiErrorResponse.of(HttpStatus.NOT_FOUND, ApiErrorCode.NOT_FOUND, "Not found", request.getRequestURI()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {
		log.error("Unhandled error on {}", request.getRequestURI(), ex);
		String message = environment.acceptsProfiles(Profiles.of("dev")) && ex.getMessage() != null
				? ex.getMessage()
				: UNEXPECTED_MESSAGE;
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrorCode.INTERNAL_ERROR, message, request.getRequestURI()));
	}
}
