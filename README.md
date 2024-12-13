Building a RESTful Web Service with Spring
Overview
Build a service to handle HTTP GET requests at http://localhost:8080/greeting.
Responds with a JSON representation of a greeting.
Default response: {"id":1,"content":"Hello, World!"}
Customizable with a name parameter: http://localhost:8080/greeting?name=User → {"id":1,"content":"Hello, User!"}

Steps
Initialize the Project

Use Spring Initializr:
Select Java and Spring Web dependency.
Choose Gradle or Maven.
Download and extract the ZIP file.
Alternatively, clone the sample repo:
git clone https://github.com/spring-guides/gs-rest-service.git
Create Resource Representation Class

Define a class to represent the greeting object:
java

package com.example.restservice;

public record Greeting(long id, String content) { }
Purpose: Converts Greeting objects into JSON automatically using Jackson (included by Spring).
Create Resource Controller

Implement a controller to handle /greeting requests:
java

package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
Key Notes:
@RestController: Combines @Controller and @ResponseBody.
@GetMapping("/greeting"): Maps HTTP GET requests.
@RequestParam: Extracts query parameter (name).
Run the Application

Spring Boot’s auto-generated main class (RestServiceApplication):
java

package com.example.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }
}
Annotations:
@SpringBootApplication: Combines:
@Configuration: Bean definitions.
@EnableAutoConfiguration: Sets up dependencies.
@ComponentScan: Detects components in the base package.
Build & Run

Gradle:
Run directly: ./gradlew bootRun
Build & run JAR:
bash

./gradlew build
java -jar build/libs/gs-rest-service-0.1.0.jar
Maven:
Run directly: ./mvnw spring-boot:run
Build & run JAR:
bash

./mvnw clean package
java -jar target/gs-rest-service-0.1.0.jar
Testing the Service
Visit http://localhost:8080/greeting:
Default response: {"id":1,"content":"Hello, World!"}
Test query parameter:
http://localhost:8080/greeting?name=User
Custom response: {"id":2,"content":"Hello, User!"}
Key Observations
@RequestParam: Handles query parameters with a default value (World) or a provided value (User).
Incrementing id: The AtomicLong counter ensures unique IDs across requests.
JSON Conversion: Automatic conversion via Spring's MappingJackson2HttpMessageConverter.
Output Example
Without query: {"id":1,"content":"Hello, World!"}
With ?name=User: {"id":2,"content":"Hello, User!"}
