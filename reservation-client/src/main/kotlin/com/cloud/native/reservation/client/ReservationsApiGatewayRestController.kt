package com.cloud.native.reservation.client

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.cloud.stream.messaging.Source
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resources
import org.springframework.http.HttpMethod.GET
import org.springframework.http.ResponseEntity
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/reservations")
class ReservationsApiGatewayRestController(val restTemplate: RestTemplate,
                                           val source: Source) {
    @GetMapping("/names")
    @HystrixCommand(fallbackMethod = "fallback")
    fun names(): List<String?> {
        val result: ResponseEntity<Resources<Reservation>> = this.restTemplate.exchange("http://reservation-service/reservations", GET,
                null, object : ParameterizedTypeReference<Resources<Reservation>>() {})

        return result.body.content.map { it.reservationName }
    }

    @PostMapping
    fun writeNames(@RequestBody reservation: Reservation) {
        val msg = MessageBuilder.withPayload(reservation.reservationName).build()
        this.source.output().send(msg)
    }

    fun fallback(): List<String> = emptyList()
}
