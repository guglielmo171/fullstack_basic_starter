package com.fullstack.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class SwaggerDevProfileTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void swaggerSpecIsOpenApi() throws Exception {
		mockMvc.perform(get("/swagger/v1/swagger.json").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.openapi").exists());
	}

	@Test
	void swaggerUiIsReachable() throws Exception {
		MvcResult result = mockMvc.perform(get("/swagger")).andReturn();
		int status = result.getResponse().getStatus();
		assertThat(status).isIn(200, 301, 302, 303, 307, 308);
		if (status >= 300 && status < 400) {
			String location = result.getResponse().getHeader("Location");
			assertThat(location).contains("/swagger");
			assertThat(location).doesNotContain("/swagger-ui");
		}
	}
}
