package com.example.testtask.service;

import com.example.testtask.ScheduledTask;
import org.hibernate.exception.GenericJDBCException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountServiceTests {

	@LocalServerPort
	private Integer port;

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
			"postgres:16-alpine"
	);

	@BeforeAll
	static void beforeAll() {
		postgres.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@MockitoBean
	private ScheduledTask scheduledTask;

	@Autowired
	AccountService accountService;

	@Test
	void shouldPerformTransfer() {
		Long userIdFrom = 1L;
		Long userIdTo = 2L;
		BigDecimal amount = BigDecimal.valueOf(100);
		accountService.transfer(userIdFrom, userIdTo, amount);
	}

	@Test
	void shouldFailTransferDueToBalanceNotEnough() {
		Long userIdFrom = 1L;
		Long userIdTo = 2L;
		BigDecimal amount = BigDecimal.valueOf(1000);
		Exception thrown = assertThrows(GenericJDBCException.class,
				() -> accountService.transfer(userIdFrom, userIdTo, amount),
				"Expected exception");
		assertTrue(thrown.getMessage().contains("account balance 100 not enough for transfer: 1000"));
	}

	@Test
	void shouldFailTransferDueToAccountNotFound() {
		Long userIdFrom = 1L;
		Long userIdTo = 3L;
		BigDecimal amount = BigDecimal.valueOf(100);
		Exception thrown = assertThrows(GenericJDBCException.class,
				() -> accountService.transfer(userIdFrom, userIdTo, amount),
				"Expected exception");
		assert(thrown.getMessage().contains("destination account not found for: 3"));
	}
}
