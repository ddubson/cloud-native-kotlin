package com.cloud.native

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Reservation(@Id @GeneratedValue var id: Long? = 0,
                       val reservationName: String)