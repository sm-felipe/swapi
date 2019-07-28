package com.ame.swapi;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableAutoConfiguration
public class AppConfiguration {

    @Bean
    public WebClient client() {
       return WebClient.create("http://127.0.0.1:8080");
    }
}
