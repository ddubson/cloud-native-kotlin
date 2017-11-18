package com.cloud.native.reservation.client

import com.github.tomakehurst.wiremock.client.WireMock.*
import org.apache.http.HttpHeaders
import org.assertj.core.api.BDDAssertions.then
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.test.context.junit4.SpringRunner
import java.io.BufferedReader

@SpringBootTest(classes = arrayOf(TestConfig::class))
@RunWith(SpringRunner::class)
@AutoConfigureWireMock(port = 9999)
internal class ReservationClientWireMockTest {
    @Autowired
    lateinit var reservationClient: ReservationClient

    private val reservations: Resource = ClassPathResource("service-response.json")

    @Test
    fun `should return reservation names`() {
        stubFor(get(urlMatching("/reservations"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/hal+json;charset=UTF-8")
                        .withBody(asJson(reservations))))
        val reservationNames = reservationClient.getReservationNames()

        then(reservationNames.size).isEqualTo(4)
        then(reservationNames[0]).isEqualTo("John")
        then(reservationNames[1]).isEqualTo("Paul")
        then(reservationNames[2]).isEqualTo("Jane")
        then(reservationNames[3]).isEqualTo("Mark")
    }

    private fun asJson(res: Resource): String =
            res.inputStream.bufferedReader().use(BufferedReader::readText)
}