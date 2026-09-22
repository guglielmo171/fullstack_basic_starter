package com.fullstack.api.health;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.api.http.ApiResponse;

@RestController
@RequestMapping("/api/v1/healthcheck")
public class HealthCheckController {

	@GetMapping("/ping")
	public ApiResponse<PingData> ping() {
		return ApiResponse.ok(new PingData("pong", false, Instant.now()));
	}
}
