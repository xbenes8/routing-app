# Routing application

### expected preconditions
* installed maven or able to call maven wrapper
* installed java 17 or higher

### commands 
* Extract **routing.zip** into some folder
* In folder execute maven commands via maven wrapper(./mvnw) or maven installed (mvn)
* Command **mvn spring-boot:run** should build and trigger application
* Or step by step via build and run commands
* Command **mvn clean install** should create jar artifact **routing-1.0.0.jar**.
* Command **java -jar routing-1.0.0.jar** should trigger application on default port 8080.
* On URL http://localhost:8080/swagger-ui/index.html is accessible swagger where you can try exposed routing endpoint. 
