package com.fullstack.api.http;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiResponse<T>(
		boolean success,
		T data,
		@JsonInclude(JsonInclude.Include.NON_NULL) String message) {

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(true, data, null);
	}
}
