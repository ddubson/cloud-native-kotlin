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

# (7) Start DataFlow server (optional)
# Port: 9393
./gradlew :dataflow-service:bootRun
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

### Viewing
To view list of reservation names via API Gateway (that relays to the reservation service via reservation client):

```
GET /reservations/names
HEADER:
- Authorization: Bearer [bearer-token]
```

This endpoint has a Circuit Breaker (Hystrix) which falls back to a method that provides
an empty list in case of failure of execution of original method.

### Creating 

To add to the list of reservations, we can POST to our gateway and send the payload over the wire as 
a message, via Spring Cloud Stream with RabbitMQ as the backing message queue.

The reservation client has a property for binding output in `reservation-client.yml` in cloud config
and has a Source binding in declaration.

`spring.cloud.stream.bindings.output.destination: reservations`

The reservation service has a property for binding input in `reservation-service.yml` in cloud config
and has a Sink binding in declaration.

`spring.cloud.stream.bindings.input.destination: reservations`

The endpoint on the Reservation client side is:

```
POST /reservations
HEADER:
- Content-Type: application/json
- Authorization: Bearer [bearer-token]

{ "reservationName" : "content" }
```

## Zuul Proxy

`reservation-client` is a Zuul proxy, which relays service requests to a configured REST API.

To access `reservation-service` REST API endpoints, `reservation-client` has endpoints such as:

```
GET /reservation-service/reservations
```

Which calls Reservation service itself, acting as a proxy.

## Hystrix Dashboard

Navigate to `http://localhost:8889/hystrix` to find the Hystrix dashboard.

To view Hystrix metrics for `reservation-client`, enter `http://localhost:8081/hystrix.stream` into the main panel

## Authentication Service (OAuth2)

An in-memory implementation of `password` grant type.

To get an OAuth token, call the Auth service:

```
POST localhost:9191/oauth/token
Headers:
- Accept: application/json
- Authorization: Basic [base64(html5/password)]
Body
- password: test123
- username: jdoe
- grant_type: password
- scope: openid
- client_secret: password
- client_id: html5
```

Reservation client is protected by OAuth2, so to be able to access any resources, each request will
need an Authorization Bearer header (with a bearer token retrieved from Auth service):

```
GET localhost:8081/reservations/names
HEADER:
- Authorization: Bearer [bearer-token]
```

## Spring Cloud DataFlow

`dataflow-service` is a Spring Boot app that loads the local DataFlow server. It is bound on port `9393`

To interact with the dataflow server, you can download the dataflow shell provided by Spring.

### Using the shell

Run the shell via:

```
java -jar spring-cloud-dataflow-shell-[version].RELEASE.jar

# Import apps
app import --uri http://bit.ly/stream-applications-rabbit-maven

# list apps available - (soures, processors, sinks, tasks)
app list

# Create a stream that reads from a file directory and writes to reservation-service
stream create --name files-to-reservations --definition " file --file.consumer.mode=lines --file.directory
=/path/to/directory > :reservations " --deploy
```

This will create a File directory source and hook up to the reservation service sink already configured,
on 'reservations' RabbitMQ queue.

