package com.cloud.native

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CustomHealthIndicator : HealthIndicator {
    private var health: Health = Health.status("Up!").build()

    @EventListener(RuntimeException::class)
    fun onRuntimeException(ex: RuntimeException) {
        this.health = Health.status("Struggling.").build()
    }

    override fun health(): Health {
        return Health.status("Whoo!").build()
    }
}