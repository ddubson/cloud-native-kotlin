package com.cloud.native.eureka

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class EurekaService

fun main(args: Array<String>) {
    SpringApplication.run(EurekaService::class.java, *args)
}