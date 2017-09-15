# Cloud Native Kotlin

Kotlin-based version of Josh Long's 'Cloud Native Java' code

## Running

Reservation Service app

```

# Start Cloud Config server on port 8888
./gradlew :cloud-config:bootRun

# Start Reservation service on port 8080
./gradlew :reservation-service:bootRun
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