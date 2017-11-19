package com.cloud.native.reservation.client

import org.springframework.hateoas.ResourceSupport

data class Reservation(var id: Long? = null,
                       var reservationName: String? = null): ResourceSupport()