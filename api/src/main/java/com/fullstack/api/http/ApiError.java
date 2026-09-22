package com.fullstack.api.http;

import java.time.Instant;
import java.util.List;

public record ApiError(
		String message,
		ApiErrorCode code,
		int statusCode,
		Instant timestamp,
		String path,
		List<ApiErrorDetail> details) {
}
