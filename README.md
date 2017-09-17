# Cloud Native Kotlin

Kotlin-based version of Josh Long's 'Cloud Native Java' code

## Running

Reservation Service app

```
# (1) Start Cloud Config server on port 8888
./gradlew :cloud-config:bootRun

# (2) Start Eureka Server on port 8761
./gradlew :eureka-service:bootRun

# (3) Start Reservation service on port 8080
./gradlew :reservation-service:bootRun

# (4) Start Reservation client (Zuul proxy, API Gateway, Circuit Breaker) 
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