package com.example.springintegrationmetricsissue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegrationManagement;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.support.GenericMessage;

import java.time.Duration;
import java.util.UUID;

import static org.springframework.integration.dsl.IntegrationFlows.from;
import static org.springframework.integration.dsl.Pollers.fixedRate;

@SpringBootApplication
// If the @EnableIntegrationManagement annotation is added, metrics are not set up
// for spring integration and the test will fail.
// If the annotation is removed, metrics are set up and the test is successful.
@EnableIntegrationManagement(defaultLoggingEnabled = "false")
public class SpringIntegrationMetricsIssueApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationMetricsIssueApplication.class, args);
    }

    @Bean
    public IntegrationFlow testFlow() {
        MessageSource<UUID> uuidSource = () -> new GenericMessage<>(UUID.randomUUID());
        return from(uuidSource, spec -> spec.poller(fixedRate(Duration.ofMillis(250))))
                .channel("testChannel")
                .log()
                .get();
    }

}
