package com.cloud.native.reservation.service

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.annotation.RestResource

@RepositoryRestResource
interface ReservationRepository : JpaRepository<Reservation, Long> {
    @RestResource(path = "by-name")
    fun findByReservationName(@Param("rn") rn: String): Collection<Reservation>
}