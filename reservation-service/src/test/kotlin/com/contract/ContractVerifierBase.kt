package com.contract

import com.cloud.native.reservation.service.ReservationController
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.Before
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier

@AutoConfigureMessageVerifier
abstract class ContractVerifierBase {
    @Before
    open fun setup() {
        RestAssuredMockMvc.standaloneSetup(ReservationController("Welcome!"))
    }
}