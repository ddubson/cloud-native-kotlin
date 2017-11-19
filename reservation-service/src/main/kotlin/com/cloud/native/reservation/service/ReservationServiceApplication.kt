package com.cloud.native.reservation.service

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Sink

@SpringBootApplication
@EnableBinding(Sink::class)
@EnableDiscoveryClient
class ReservationServiceApplication(val reservationRepository: ReservationRepository)
    : CommandLineRunner {

    override fun run(vararg args: String?) {
        listOf("John", "Paul", "Jane", "Mark").forEach {
            reservationRepository.save(Reservation(0, it))
        }

        reservationRepository.findAll().forEach { println(it) }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(ReservationServiceApplication::class.java, *args)
}