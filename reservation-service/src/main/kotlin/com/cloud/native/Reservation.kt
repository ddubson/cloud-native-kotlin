package com.cloud.native

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Reservation(@Id @GeneratedValue var id: Long,
                       val reservationName: String) {
    constructor(reservationName: String): this(0, reservationName)
}