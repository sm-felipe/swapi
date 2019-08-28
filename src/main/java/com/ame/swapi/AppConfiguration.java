package com.ame.swapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.ApiInfoBuilder;


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

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .enableUrlTemplating(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ps.account.association.api.rest"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Account Association API")
                .description("Account Association API")
                .version("1.0.0")
                .build();
    }
}
