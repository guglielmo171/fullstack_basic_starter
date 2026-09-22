package com.fullstack.api.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

class ApiExceptionHandlerTests {

	@Test
	void masksUnexpectedErrorOutsideDev() {
		ApiExceptionHandler handler = new ApiExceptionHandler(new StandardEnvironment());
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/healthcheck/ping");

		var response = handler.handleUnexpected(new RuntimeException("secret detail"), request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().success()).isFalse();
		assertThat(response.getBody().error().code()).isEqualTo(ApiErrorCode.INTERNAL_ERROR);
		assertThat(response.getBody().error().statusCode()).isEqualTo(500);
		assertThat(response.getBody().error().message()).isEqualTo(ApiExceptionHandler.UNEXPECTED_MESSAGE);
		assertThat(response.getBody().error().details()).isEmpty();
		assertThat(response.getBody().error().path()).isEqualTo("/api/v1/healthcheck/ping");
		assertThat(response.getBody().error().timestamp()).isNotNull();
	}

	@Test
	void keepsExceptionMessageInDev() {
		StandardEnvironment environment = new StandardEnvironment();
		environment.setActiveProfiles("dev");
		ApiExceptionHandler handler = new ApiExceptionHandler(environment);
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/healthcheck/ping");

		var response = handler.handleUnexpected(new RuntimeException("secret detail"), request);

		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().error().message()).isEqualTo("secret detail");
	}
}
