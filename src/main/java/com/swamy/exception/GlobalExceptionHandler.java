package com.swamy.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.swamy.dto.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {

		ErrorDetails errorDetails = ErrorDetails.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
				.statusCode(HttpStatus.NOT_FOUND.value()).timeStamp(LocalDateTime.now())
				.path(webRequest.getDescription(false)).build();

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = BlogApiException.class)
	public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException exception, WebRequest webRequest) {

		ErrorDetails errorDetails = ErrorDetails.builder().message(exception.getMessage())
				.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value())
				.timeStamp(LocalDateTime.now()).path(webRequest.getDescription(false)).build();

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorDetails> handleExceptions(Exception exception, WebRequest webRequest) {

		ErrorDetails errorDetails = ErrorDetails.builder().message(exception.getMessage())
				.status(HttpStatus.INTERNAL_SERVER_ERROR).statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.timeStamp(LocalDateTime.now()).path(webRequest.getDescription(false)).build();

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, String> errors = new ConcurrentHashMap<>();

		exception.getBindingResult().getAllErrors().forEach(error -> {
			String field = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(message, field);
		});

		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
}
