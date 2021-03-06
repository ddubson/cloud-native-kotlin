package com.cloud.reservation.client

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resources
import org.springframework.http.HttpMethod.GET
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ReservationClient(val restTemplate: RestTemplate,
                        @Value("\${service.reservation}") private val reservationServiceUrl: String) {
    @HystrixCommand(fallbackMethod = "fallback")
    fun getReservationNames(): List<String> {
        val result = this.restTemplate
                .exchange(
                        "$reservationServiceUrl/reservations",
                        GET,
                        null,
                        object : ParameterizedTypeReference<Resources<Reservation>>() {})

        return result.body.content.mapNotNull { it.reservationName }
    }

    fun getReservationById(reservationId: Int): Reservation {
        val result = this.restTemplate.exchange(
                "$reservationServiceUrl/reservations/$reservationId",
                GET,
                null,
                Reservation::class.java)

        return result.body
    }

    fun fallback(): List<String> = emptyList()
}