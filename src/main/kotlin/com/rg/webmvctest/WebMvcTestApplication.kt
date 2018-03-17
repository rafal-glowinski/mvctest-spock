package com.rg.webmvctest

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class WebMvcTestApplication

fun main(args: Array<String>) {
    SpringApplication.run(WebMvcTestApplication::class.java, *args)
}
