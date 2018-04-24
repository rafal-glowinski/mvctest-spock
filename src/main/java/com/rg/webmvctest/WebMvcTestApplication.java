package com.rg.webmvctest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebMvcTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebMvcTestApplication.class, args);
    }

    /**
     * For Spring Boot 1.4.x post-processor is required to perform validation on request body using: javax.validation.Valid.
     *
     * We don't use it here since it is a Spring Boot 1.5.x based app.
     */
//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
//        return new MethodValidationPostProcessor();
//    }
}
