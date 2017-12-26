package com.cloud.reservation.client

import org.assertj.core.api.BDDAssertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.verifier.messaging.noop.NoOpContractVerifierAutoConfiguration
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest(classes = [TestConfig::class, NoOpContractVerifierAutoConfiguration::class])
@RunWith(SpringRunner::class)
@AutoConfigureStubRunner(ids = ["com.cloud:reservation-service:+:stubs:9999"], workOffline = true)
internal class ReservationClientWireMockCloudContractTest {
    @Autowired
    lateinit var reservationClient: ReservationClient

    @Test
    fun `should return reservation names`() {
        val reservationNames = reservationClient.getReservationNames()

        BDDAssertions.then(reservationNames.size).isEqualTo(4)
        BDDAssertions.then(reservationNames[0]).isEqualTo("John")
        BDDAssertions.then(reservationNames[1]).isEqualTo("Paul")
        BDDAssertions.then(reservationNames[2]).isEqualTo("Jane")
        BDDAssertions.then(reservationNames[3]).isEqualTo("Mark")
    }

}