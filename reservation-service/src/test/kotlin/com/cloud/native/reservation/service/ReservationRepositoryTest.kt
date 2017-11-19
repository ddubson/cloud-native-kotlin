package com.cloud.native.reservation.service

import org.assertj.core.api.BDDAssertions.then
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(ReservationServiceApplication::class))
@DataJpaTest
internal class ReservationRepositoryTest {
    @Autowired
    lateinit var testEntityManager: TestEntityManager

    @Autowired
    lateinit var reservationRepository: ReservationRepository

    @Test
    fun `save should create a new reservation`() {
        val reservation = Reservation(0, "a reservation")
        val savedReservation = this.testEntityManager.persistFlushFind(reservation)
        then(savedReservation.id).isNotNull()
        then(savedReservation.reservationName).isEqualToIgnoringCase("a reservation")
    }

    @Test
    fun `findByName should find reservations by name`() {
        val reservation = Reservation(0, "rev1")
        this.testEntityManager.persist(reservation)
        val foundReservations: List<Reservation> = reservationRepository
                .findByReservationName("rev1") as List<Reservation>
        then(foundReservations.size).isEqualTo(1)
        then(foundReservations[0].reservationName).isEqualTo("rev1")
    }
}