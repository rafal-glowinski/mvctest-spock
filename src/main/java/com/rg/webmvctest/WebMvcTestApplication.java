package com.rg.webmvctest;

import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
public class WebMvcTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebMvcTestApplication.class, args);
    }

    /**
     * This post-processor is required to perform validation on request body using: javax.validation.Valid
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public KotlinModule kotlinModule() {
        return new KotlinModule();
    }
}
