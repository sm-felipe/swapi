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
    private String blockingAppURL;

    @Value("${swapi-url}")
    private String swapiURL;

    @Bean(name = "blockingAppClient")
    public WebClient blockingAppClient() {
       return WebClient.create(blockingAppURL);
    }

    @Bean(name = "swapiClient")
    public WebClient swapiClient() {
        return WebClient.create(swapiURL);
    }
}
