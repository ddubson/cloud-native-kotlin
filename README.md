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

## Spring Boot Actuator

Base Url for actuator endpoints are located at `/admin/`

### Git commit info of running instance

Git commit information can be found at `/info`