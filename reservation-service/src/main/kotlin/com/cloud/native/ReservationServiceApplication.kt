package com.cloud.native

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ReservationServiceApplication(val reservationRepository: ReservationRepository)
    : CommandLineRunner {

    override fun run(vararg args: String?) {
        listOf("John", "Paul", "Jane", "Mark").forEach { name ->
            reservationRepository.save(Reservation(0, name))
        }

        reservationRepository.findAll().forEach { reservation ->
            println(reservation)
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(ReservationServiceApplication::class.java, *args)
}