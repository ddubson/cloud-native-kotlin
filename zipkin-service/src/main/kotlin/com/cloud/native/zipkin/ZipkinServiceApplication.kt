package com.cloud.native.zipkin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import zipkin.server.EnableZipkinServer

@EnableZipkinServer
@EnableDiscoveryClient
@SpringBootApplication
class ZipkinServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(ZipkinServiceApplication::class.java, *args)
}