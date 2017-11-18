package com.cloud.native.reservation.client

import org.assertj.core.api.BDDAssertions.then
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestTemplate

@SpringBootTest(classes = arrayOf(TestConfig::class))
@RunWith(SpringRunner::class)
internal class ReservationClientRestServiceServerTest {
    private val serviceResponse: Resource = ClassPathResource("service-response.json")
    lateinit var mockServer: MockRestServiceServer

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var reservationClient: ReservationClient

    @Before
    fun before() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build()
    }

    @Test
    fun `should return reservation names`() {
        mockServer.expect(
                ExpectedCount.manyTimes(),
                requestTo("http://localhost:9999/reservations"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(serviceResponse, MediaType.parseMediaType("application/hal+json;charset=UTF-8")))

        val reservationNames = reservationClient.getReservationNames()

        mockServer.verify()
        then(reservationNames.size).isEqualTo(4)
        then(reservationNames[0]).isEqualTo("John")
        then(reservationNames[1]).isEqualTo("Paul")
        then(reservationNames[2]).isEqualTo("Jane")
        then(reservationNames[3]).isEqualTo("Mark")
    }
}