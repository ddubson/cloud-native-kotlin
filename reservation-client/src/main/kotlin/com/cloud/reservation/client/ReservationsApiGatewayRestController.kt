package com.cloud.reservation.client

import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reservations")
class ReservationsApiGatewayRestController(val reservationClient: ReservationClient,
                                           val source: Source) {
    @GetMapping("/names")
    fun names(): List<String> {
        return this.reservationClient.getReservationNames()
    }

    @PostMapping
    fun writeNames(@RequestBody reservation: Reservation) {
        val msg = MessageBuilder.withPayload(reservation.reservationName).build()
        this.source.output().send(msg)
    }
}
