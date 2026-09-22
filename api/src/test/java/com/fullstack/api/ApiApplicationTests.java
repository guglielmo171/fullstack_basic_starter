package com.fullstack.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
class ApiApplicationTests {

	@Autowired
	private Environment environment;

	@Test
	void contextLoadsWithPhase00Excludes() {
		assertThat(environment.getProperty("server.port")).isEqualTo("5001");
		assertThat(exclude(0)).contains("DataSourceAutoConfiguration");
		assertThat(exclude(1)).contains("DataRedisAutoConfiguration");
		assertThat(exclude(2)).contains("LiquibaseAutoConfiguration");
	}

	private String exclude(int index) {
		return environment.getProperty("spring.autoconfigure.exclude[" + index + "]");
	}

}
