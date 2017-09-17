package com.cloud.native

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

interface ReservationChannels {
    @Input
    fun input(): SubscribableChannel
}