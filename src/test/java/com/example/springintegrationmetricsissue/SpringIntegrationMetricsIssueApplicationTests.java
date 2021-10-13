package com.example.springintegrationmetricsissue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class SpringIntegrationMetricsIssueApplicationTests {

    // This test fails if the @EnableIntegrationManagement annotation is
    // present in the application configuration
    @Test
    void testSpringIntegrationMetrics(@Autowired TestRestTemplate restTemplate) throws InterruptedException {
        Thread.sleep(500);
        String body = restTemplate.getForObject("/actuator/metrics", String.class);
        assertThat(body).contains("\"spring.integration.channels\"");
    }

}
