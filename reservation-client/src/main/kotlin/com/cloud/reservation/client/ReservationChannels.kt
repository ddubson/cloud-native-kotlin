package com.cloud.reservation.client

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface ReservationChannels {
    @Output
    fun output(): MessageChannel
}