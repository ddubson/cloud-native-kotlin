package com.cloud.native.reservation.client

import org.assertj.core.api.BDDAssertions.then
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
internal class ReservationClientRestServiceServerTest {
    @Autowired
    lateinit var apiGateway: ReservationsApiGatewayRestController

    @Test
    fun `should return reservation names`() {
        val reservationNames = apiGateway.names()
        then(reservationNames.size).isEqualTo(4)
    }
}