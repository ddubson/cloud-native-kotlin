package com.cloud.reservation.service.contract;

import com.cloud.reservation.service.ReservationServiceApplication;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMessageVerifier
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ReservationServiceApplication.class})
public abstract class ReservationControllerBase {

    @Autowired
    private WebApplicationContext webAppContextSetup;

    @Before
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(webAppContextSetup);
    }
}