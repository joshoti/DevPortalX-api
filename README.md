# DevPortalX-api
A centralized developer portal providing APIs, resources, and tools for seamless integration with our platform. DevPortalX enables developers to connect their apps, manage integrations, and explore documentation in one place.

- See React frontend [here](https://github.com/joshoti/DevPortalX)

- See [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/1.1.0.M1/reference/html/index.html) for more info.

## API Documentation
After starting the application, access the Swagger API documentation at:
http://localhost:8080/swagger-ui/

## Features
- API Documentation and Key Management
- User Authentication and OAuth 2.0 Support
- Analytics Dashboard for Tracking API Usage
- Webhooks and Event Subscriptions
- Developer Tools and SDK Access

# Getting Started

## Prerequisites
Ensure you have the following installed:
- JDK 17+
- Maven 3+
- PostgreSQL 13+
- Docker (optional)

## Installation
1. Clone the repository:
```bash
git clone https://github.com/joshoti/DevPortalX-api.git
cd DevPortalX-api
```
2. Configure application properties:
Edit the `src/main/resources/application.properties` file:
```bash
spring.datasource.url=jdbc:h2:mem:devportalxdb;NON_KEYWORDS=user
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.h2.console.enabled=true
server.error.include-message=always
```
2.1. If you would like to have a persistent database storage, update the first two settings above to the below:
```bash
spring.datasource.url=jdbc:h2:file:./data/devportalxdb;NON_KEYWORDS=user
spring.jpa.hibernate.ddl-auto=update
```

3. Build and run the application:
```bash
mvn clean install
mvn spring-boot:run
```

4. Access the application:
The application should now be running at `http://localhost:8080`.


## Contributing
Contributions are welcome! Please open an issue or submit a pull request for any enhancements, bug fixes, or new features.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
