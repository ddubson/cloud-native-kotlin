package com.cloud.reservation.service.contract;

import com.cloud.reservation.service.Reservation;
import com.cloud.reservation.service.ReservationRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.mockito.BDDMockito.given;

@AutoConfigureMessageVerifier
@RunWith(MockitoJUnitRunner.class)
public abstract class ReservationControllerBase {
    @Mock
    ReservationRepository reservationRepository;

    @Before
    public void setup() {
        List<Reservation> reservationList = Stream.of("John", "Paul", "Jane", "Mark")
                .map(name -> new Reservation(0, name)).collect(toList());

        given(reservationRepository.findAll()).willReturn(reservationList);
        RestAssuredMockMvc.standaloneSetup(reservationRepository);
    }
}