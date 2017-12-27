import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "/reservations/{id} should return single reservation by Id"

    request {
        method GET()
        url "/reservations/1"
    }

    response {
        status 200
        headers {
            contentType("application/hal+json;charset=UTF-8")
        }
        body("""
            {
                "reservationName": "John",
            }
        """)
    }
}