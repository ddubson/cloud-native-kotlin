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
            header("Content-Type", "application/hal+json;charset=UTF-8")
        }
        body
            """
            "_embedded": {
                "reservations": [
                        {
                            "reservationName": "John",
                            "_links": {
                            "self": {
                                "href": "http://localhost:9999/reservations/1"
                            },
                            "reservation": {
                                "href": "http://localhost:9999/reservations/1"
                            }
                        }
                        },
                        {
                            "reservationName": "Paul",
                            "_links": {
                            "self": {
                                "href": "http://localhost:9999/reservations/2"
                            },
                            "reservation": {
                                "href": "http://localhost:9999/reservations/2"
                            }
                        }
                        },
                        {
                            "reservationName": "Jane",
                            "_links": {
                            "self": {
                                "href": "http://localhost:9999/reservations/3"
                            },
                            "reservation": {
                                "href": "http://localhost:9999/reservations/3"
                            }
                        }
                        },
                        {
                            "reservationName": "Mark",
                            "_links": {
                            "self": {
                                "href": "http://localhost:9999/reservations/4"
                            },
                            "reservation": {
                                "href": "http://localhost:9999/reservations/4"
                            }
                        }
                        }
                ]
            },
            "_links": {
                "self": {
                    "href": "http://localhost:9999/reservations{?page,size,sort}"
                },
                "profile": {
                    "href": "http://localhost:9999/profile/reservations"
                },
                "search": {
                    "href": "http://localhost:9999/reservations/search"
                }
            }
        """
    }
}