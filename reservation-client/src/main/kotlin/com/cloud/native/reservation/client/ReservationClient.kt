package com.cloud.native.reservation.client

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
        //val traverson = Traverson(URI("$reservationServiceUrl/reservations"), MediaTypes.HAL_JSON)
        //val tBuilder = this.restTemplate.follow("users")
/*        ParameterizedTypeReference<Resources<UserJson>> typeRefDevices = new ParameterizedTypeReference<Resources<UserJson>>() {};
Resources<UserJson> resUsers = tb.toObject(typeRefDevices);
Collection<UserJson> users= resUsers .getContent();*/


        val result = this.restTemplate
                .exchange(
                        "$reservationServiceUrl/reservations",
                        GET,
                        null,
                        object : ParameterizedTypeReference<Resources<Reservation>>() {})

        return result.body.content.mapNotNull { it.reservationName }
    }

    fun fallback(): List<String> = emptyList()
}