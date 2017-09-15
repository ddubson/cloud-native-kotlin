package com.cloud.native

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
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