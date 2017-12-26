package com.cloud.reservation.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.context.annotation.Bean
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.web.client.RestTemplate

@EnableBinding(Source::class)
@SpringBootApplication
@EnableZuulProxy
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableResourceServer
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