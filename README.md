# Cloud Native Kotlin

Kotlin-based version of Josh Long's 'Cloud Native Java' code

## Pre-requisites

- RabbitMQ installed and running.

## Running

Reservation Application Microservice ecosystem

```bash
# (1) Start Cloud Config server (wait until started)
# Port: 8888
./gradlew :cloud-config:bootRun

# (2) Start Eureka Server
# Port: 8761
./gradlew :eureka-service:bootRun

# (3) Start Auth Service (OAuth2)
# Port: 9191
./gradlew :auth-service:bootRun

# (3) Start Hystrix Dashboard 
# Port: 8889
./gradlew :hystrix-dashboard:bootRun

# (4) Start Zipkin Service (Distributed message tracing)
# Port: 9411
./gradlew :zipkin-service:bootRun

# (5) Start Reservation service (microservice)
# Port: 8080
./gradlew :reservation-service:bootRun

# (6) Start Reservation client (Zuul proxy, API Gateway, Circuit Breaker) 
# Port: 8081
./gradlew :reservation-client:bootRun
```

## Spring Cloud Config

The configuration properties are served from a Spring Cloud
Config Server, running on port `8888`

Some beans that have `@RefreshScope` annotation can have properties
refreshed without restarting. If a property value changed on the config server,
to trigger a refresh, execute `POST /admin/refresh` on the microservice itself.

## Spring Boot Actuator

Base Url for actuator endpoints are located at `/admin/`

### Git commit info of running instance

Git commit information can be found at `/admin/info`

## API Gateway

To view list of reservation names via API Gateway (that relays to the reservation service via reservation client):

```
GET /reservations/names
```

This endpoint has a Circuit Breaker (Hystrix) which falls back to a method that provides
an empty list in case of failure of execution of original method.

## Zuul Proxy

`reservation-client` is a Zuul proxy, which relays service requests to a configured REST API.

To access `reservation-service` REST API endpoints, `reservation-client` has endpoints such as:

```
/reservation-service/reservations
```

Which calls Reservation service itself, acting as a proxy.

## Hystrix Dashboard

Navigate to `localhost:8889/hystrix` to find the Hystrix dashboard.

To view Hystrix metrics for `reservation-client`, enter `http://localhost:8081/hystrix.stream` into the main panel

## Authentication Service (OAuth2)

To get an OAuth token:

```
POST localhost:9191/uaa/oauth/token
Headers:
- Accept: application/json
- Authorization: Basic aHRtbDU6c2VjcmV0 (html5/secret)
Body
- password: test123
- username: jdoe
- grant_type: password
- scope: openid
- client_secret: secret
- client_id: html5
```

