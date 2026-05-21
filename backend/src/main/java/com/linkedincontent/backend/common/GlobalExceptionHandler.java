package com.linkedincontent.backend.common;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.linkedincontent.backend.post.PostNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException exception,
			HttpServletRequest request) {
		String message = exception.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining("; "));

		return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", message, request.getRequestURI());
	}

	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handlePostNotFoundException(PostNotFoundException exception,
			HttpServletRequest request) {
		return buildErrorResponse(HttpStatus.NOT_FOUND, "Post not found", exception.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGenericException(Exception exception, HttpServletRequest request) {
		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", exception.getMessage(),
				request.getRequestURI());
	}

	private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String error, String message,
			String path) {
		ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), status.value(), error, message, path);
		return ResponseEntity.status(status).body(response);
	}
}
