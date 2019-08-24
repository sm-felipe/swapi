package com.ame.swapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableAutoConfiguration
public class AppConfiguration {

    @Value("${blocking-app-url}")
    String blockingAppUrl;

    @Bean
    public WebClient client() {
       return WebClient.create(blockingAppUrl);
    }
}
