package com.ame.swapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@EnableJpaRepositories
//@EnableAutoConfiguration
@Import(AppConfiguration.class)
public class SwapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwapiApplication.class, args);
    }

}
