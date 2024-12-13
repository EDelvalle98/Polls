Complete Guide to Exception Handling in Spring Boot
-

## Table of Contents
Introduction
Spring Boot’s Default Exception Handling Mechanism
Customizing Error Responses with @ResponseStatus
Using @ExceptionHandler for Fine-Grained Control
Global Exception Handling with @ControllerAdvice
Practical Considerations and Best Practices
Advanced Techniques with ResponseEntityExceptionHandler

#### 1. Introduction

Spring Boot simplifies exception handling by allowing developers to focus on business logic while the framework manages error reporting. Built-in tools include:

* @ResponseStatus to define HTTP status codes.
* @ExceptionHandler for method-level exception handling.
* @ControllerAdvice for global exception handling.

#### Objective: Build meaningful, user-friendly error responses and ensure maintainable code.

#### 2. Spring Boot’s Default Exception Handling Mechanism

####    Default Behavior:

   When an exception is thrown, Spring Boot provides a standard error response:

json

{

"timestamp": "2020-11-28T13:24:02.239+00:00",

"status": 500,

"error": "Internal Server Error",

"message": "",

"path": "/product/1"

}

### Customizing Default Behavior:

#### Modify application.yml to enhance error responses:

yaml

server:

error:

include-message: always

include-stacktrace: on_trace_param

### Example Response with Customizations:

json

{

"timestamp": "2020-11-29T09:42:12.287+00:00",

"status": 500,

"error": "Internal Server Error",

"message": "Item with id 1 not found",

"trace": "stack trace here"

}
3. Customizing Error Responses with @ResponseStatus
   Use @ResponseStatus to define specific HTTP statuses for exceptions:

java

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchElementFoundException extends RuntimeException {
public NoSuchElementFoundException(String message) {
super(message);
}
}
Improved Error Response:
json

{
"timestamp": "2020-11-29T09:42:12.287+00:00",
"status": 404,
"error": "Not Found",
"message": "Item with id 1 not found",
"path": "/product/1"
}
Alternatively, extend ResponseStatusException for added flexibility:

java
Copy code
public class NoSuchElementFoundException extends ResponseStatusException {
public NoSuchElementFoundException(String message) {
super(HttpStatus.NOT_FOUND, message);
}
}
4. Using @ExceptionHandler for Fine-Grained Control
   Define exception handling methods within a controller:

java
Copy code
@RestController
public class ProductController {

    @ExceptionHandler(NoSuchElementFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoSuchElementFoundException(
        NoSuchElementFoundException exception
    ) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(404, exception.getMessage()));
    }
}
Custom Payload Design:
java
Copy code
@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
private final int status;
private final String message;
private String stackTrace;
private List<ValidationError> errors;

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }
}
5. Global Exception Handling with @ControllerAdvice
   Use @ControllerAdvice to manage exceptions across multiple controllers:

java
Copy code
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(
        ItemNotFoundException ex
    ) {
        return new ResponseEntity<>(new ErrorResponse(404, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(500, "An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
Scope Limitation:
Use @ControllerAdvice("com.package") to target specific packages.
Limit to annotated controllers: @ControllerAdvice(annotations = SpecificAnnotation.class).
6. Practical Considerations and Best Practices
   Logging:
   Log all uncaught exceptions for debugging purposes.
   Skip logging validation errors caused by client mistakes.
   Stack Trace in Responses:
   Enable stack traces for development by adding a trace=true query parameter. Disable it in production:

java
Copy code
private boolean isTraceEnabled(WebRequest request) {
return Arrays.asList(request.getParameterValues("trace")).contains("true");
}
7. Advanced Techniques with ResponseEntityExceptionHandler
   Extend ResponseEntityExceptionHandler for built-in Spring exceptions:

java
Copy code
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, 
        HttpHeaders headers, 
        HttpStatus status, 
        WebRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(), 
            "Validation error"
        );
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errorResponse.addValidationError(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
By leveraging Spring Boot's exception handling capabilities, you can craft a robust, user-friendly, and maintainable error management system.