package com.cloud.native.reservation.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
class ReservationClientApplication {
    @Bean
    fun jsonMapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    @Bean
    @LoadBalanced
    fun restTemplate(): RestTemplate = RestTemplate()
}

fun main(args: Array<String>) {
    SpringApplication.run(ReservationClientApplication::class.java, *args)
}