package com.rg.webmvctest

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor

@SpringBootApplication
open class WebMvcTestApplication {

    /**
     * This post-processor is required to perform validation on request body using: javax.validation.Valid
     */
    @Bean
    open fun methodValidationPostProcessor(): MethodValidationPostProcessor = MethodValidationPostProcessor()

    @Bean
    open fun kotlinModule(): KotlinModule = KotlinModule()
}

fun main(args: Array<String>) {
    SpringApplication.run(WebMvcTestApplication::class.java, *args)
}
