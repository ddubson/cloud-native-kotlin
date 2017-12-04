import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "/reservations should return all reservations"

    request {
        url "/reservations"
        method GET()
    }

    response {
        status 200
        headers {
            contentType("application/hal+json;charset=UTF-8")
        }
        body("""
        {
            "_embedded": {
                "reservations": [
                        {
                            "reservationName": "John"
                        },
                        {
                            "reservationName": "Paul"
                        },
                        {
                            "reservationName": "Jane"
                        },
                        {
                            "reservationName": "Mark"
                        }
                ]
            }
        }""")
    }
}