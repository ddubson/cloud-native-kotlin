package com.cloud.native.reservation.service

import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Component

@Component
class ReservationProcessor(val reservationRepository: ReservationRepository) {
    private val log = LoggerFactory.getLogger(ReservationProcessor::class.java)

    @StreamListener("input")
    fun acceptNewReservations(reservationName: String) {
        log.info("Received message :: $reservationName")
        this.reservationRepository.save(Reservation(0,reservationName))
    }
}