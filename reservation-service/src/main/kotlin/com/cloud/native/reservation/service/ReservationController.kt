package com.cloud.native.reservation.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RefreshScope
class ReservationController(@Value("\${message.welcome}") private val welcomeMessage: String) {
    @GetMapping("/")
    fun welcome() = ResponseEntity.ok(welcomeMessage)
}