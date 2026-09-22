package com.fullstack.api.http;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;

public record ApiErrorResponse(boolean success, ApiError error) {

	public static ApiErrorResponse of(HttpStatus status, ApiErrorCode code, String message, String path) {
		return new ApiErrorResponse(false, new ApiError(
				message,
				code,
				status.value(),
				Instant.now(),
				path,
				List.of()));
	}
}
