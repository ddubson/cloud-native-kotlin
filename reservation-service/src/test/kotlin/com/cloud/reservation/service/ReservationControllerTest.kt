package com.cloud.reservation.service

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(classes = [ReservationServiceApplication::class])
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
class ReservationControllerTest {
    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun `reservation by id should return a jsonified reservation`() {
        this.mvc.perform(MockMvcRequestBuilders.get("/reservations/1"))
                .andExpect(status().is2xxSuccessful)
                .andExpect(content().contentType("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("@.reservationName").value("John"))
    }
}