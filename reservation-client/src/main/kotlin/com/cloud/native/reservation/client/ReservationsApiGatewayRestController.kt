package com.cloud.native.reservation.client

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resources
import org.springframework.http.HttpMethod.GET
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/reservations")
class ReservationsApiGatewayRestController(val restTemplate: RestTemplate) {
    @GetMapping("/names")
    @HystrixCommand(fallbackMethod = "fallback")
    fun names(): List<String?> {
        val result: ResponseEntity<Resources<Reservation>> = this.restTemplate.exchange("http://reservation-service/reservations", GET,
                null, object : ParameterizedTypeReference<Resources<Reservation>>() {})

        return result.body.content.map { it.reservationName }
    }

    fun fallback(): List<String> = emptyList()
}
