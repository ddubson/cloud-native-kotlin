package com.cloud.native.reservation.service

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

interface ReservationChannels {
    @Input
    fun input(): SubscribableChannel
}