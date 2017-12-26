package com.cloud.reservation.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.hal.Jackson2HalModule
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
@Import(ReservationClient::class)
class TestConfig {
    @Bean
    fun jsonMapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    @Bean
    fun getRestTemplateWithHalMessageConverter(): RestTemplate {
        val restTemplate = RestTemplate()
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
        halConverter.supportedMediaTypes = listOf(MediaTypes.HAL_JSON)
        halConverter.objectMapper = objectMapper
        return halConverter
    }
}