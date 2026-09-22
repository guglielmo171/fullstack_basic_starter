package com.fullstack.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HttpShellTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void pingReturnsEnvelope() throws Exception {
		mockMvc.perform(get("/api/v1/healthcheck/ping"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.message").value("pong"))
				.andExpect(jsonPath("$.data.cacheConnected").value(false))
				.andExpect(jsonPath("$.data.timestamp").isString())
				.andExpect(jsonPath("$.data.timestamp").isNotEmpty());
	}

	@Test
	void anonymousUsersRouteIs401Envelope() throws Exception {
		mockMvc.perform(get("/api/v1/users"))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.error.code").value("UNAUTHORIZED"))
				.andExpect(jsonPath("$.error.statusCode").value(401))
				.andExpect(jsonPath("$.error.message").isNotEmpty())
				.andExpect(jsonPath("$.error.path").value("/api/v1/users"))
				.andExpect(jsonPath("$.error.details").isArray())
				.andExpect(jsonPath("$.error.timestamp").isString());
	}

	@Test
	void postPingWithoutCsrfTokenIsNot403() throws Exception {
		int status = mockMvc.perform(post("/api/v1/healthcheck/ping"))
				.andReturn()
				.getResponse()
				.getStatus();
		assertThat(status).isNotEqualTo(403);
	}

	@Test
	void swaggerSpecIsNotOpenApiOutsideDev() throws Exception {
		String body = mockMvc.perform(get("/swagger/v1/swagger.json"))
				.andReturn()
				.getResponse()
				.getContentAsString();
		assertThat(body).doesNotContain("\"openapi\"");
	}
}
