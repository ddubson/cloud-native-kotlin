package com.ddubson.cloud.native.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class ConfigService

fun main(args: Array<String>) {
    SpringApplication.run(ConfigService::class.java, *args)
}