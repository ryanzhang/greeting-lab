package com.example.greetinglab.errorhandle;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = ex.getLocalizedMessage();
		this.debugMessage = ExceptionUtils.getStackTrace(ex);
	}

	ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ExceptionUtils.getStackTrace(ex);
	}

}