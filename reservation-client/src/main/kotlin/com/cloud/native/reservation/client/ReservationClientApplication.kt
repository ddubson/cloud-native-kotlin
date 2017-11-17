package com.cloud.native.reservation.client

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
import org.springframework.hateoas.MediaTypes.HAL_JSON
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.hal.Jackson2HalModule
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
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
    fun getRestTemplateWithHalMessageConverter(): RestTemplate {
        val restTemplate = RestTemplate();
        val existingConverters: List<HttpMessageConverter<*>> = restTemplate.messageConverters
        val newConverters = mutableListOf<HttpMessageConverter<*>>()
        newConverters.add(getHalMessageConverter())
        newConverters.addAll(existingConverters)
        restTemplate.messageConverters = newConverters
        return restTemplate
    }

    fun getHalMessageConverter(): HttpMessageConverter<*> {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(Jackson2HalModule())
        val halConverter = TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport::class.java)
        halConverter.supportedMediaTypes = listOf(HAL_JSON)
        halConverter.objectMapper = objectMapper
        return halConverter
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(ReservationClientApplication::class.java, *args)
}