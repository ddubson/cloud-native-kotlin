package com.cloud.reservation.client

import org.assertj.core.api.BDDAssertions.then
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.verifier.messaging.noop.NoOpContractVerifierAutoConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(
        properties = ["service.reservation=http://localhost:9998"],
        classes = [TestConfig::class, NoOpContractVerifierAutoConfiguration::class])
@AutoConfigureStubRunner(
        ids = ["com.cloud:reservation-service:+:stubs:9998"],
        workOffline = true)
internal class ReservationClientWireMockCloudContractTest {
    @Autowired
    lateinit var reservationClient: ReservationClient

    @Test
    fun `should return reservation names`() {
        val reservationNames = reservationClient.getReservationNames()

        then(reservationNames.size).isEqualTo(4)
        then(reservationNames[0]).isEqualTo("John")
        then(reservationNames[1]).isEqualTo("Paul")
        then(reservationNames[2]).isEqualTo("Jane")
        then(reservationNames[3]).isEqualTo("Mark")
    }

    @Test
    fun `should return reservation by ID`() {
        val reservation = reservationClient.getReservationById(1)

        then(reservation.reservationName).isEqualTo("John")
    }
}