What is Spring?
-
Spring(Framework) focuses on creating enterprise applications with Java
Lots of boilerplate code no longer necessary
Offers IoC(Inversion of Control), transactions
Spring has a very many different parts

Spring vs Spring Boot
-
Spring Boot is built on top of spring framework
Auto configured
Custom configuration happens with annotations(@Annotation), XML configuration no longer necessary
Easy to set up
Great for standalone APIs

Spring Bean: A Spring-managed object, created and maintained by the Spring container, typically representing a service or component in an application.
-
Dependency Injection (DI): A design pattern where an object's dependencies are provided externally rather than being created within the object itself.
-
IOC Container: A core component in Spring, managing object creation, lifecycle, and dependency injection.
-
Application Context: A specialized IOC container that also supports additional features like event propagation and AOP (Aspect-Oriented Programming).
-
@SpringBootApplication: A convenience annotation that enables component scanning, auto-configuration, and configuration for Spring Boot applications.
-
Dispatch Servlet: A central dispatcher in Spring MVC that handles all HTTP requests and forwards them to the appropriate controllers.
-
Logger: A tool for recording log messages, useful for tracking application behavior and diagnosing issues.
-