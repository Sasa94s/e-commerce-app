# eCommerce Application

Securely Shopping application, where User can submit orders, view orders history, manage the cart, and show shopping
items after securely login with a valid created account.

In this project, I've demonstrated the security and DevOps skills, by handling authentication and authorization
controls, so users can only access their data in a secure way.

## Table of Content

- [Stack](#stack)
- [Authentication and Authorization](#authentication-and-authorization)
- [Testing](#testing)
- [API Documentation](./API.md)
- [CI/CD Guide](./CI-CD.md)
- [Splunk Report](SPLUNK.md)

## Stack

Technologies Used:

- Spring Boot
- Spring Security
- Maven
- Tomcat
- Jenkins
- Docker

In this project, you'll have an opportunity to demonstrate the security and DevOps skills that you learned in this
lesson by completing an eCommerce application. You'll start with a template for the complete application, and your goal
will be to take this template and add proper authentication and authorization controls so users can only access their
data, and that data can only be accessed in a secure way.

## Authentication and Authorization

Password based authentication scheme is implemented using BCrypt for hashing passwords to be stored. Password is
validated against some rules:

- Length (8 ~ 16 characters)
- One Upper-case character at least
- One Lower-case character at least
- One Digit character at least
- One Symbol at least
- No Whitespaces
- No Sequence characters or digits (Less than 5 characters is allowed)

Spring Security configuration is customized by Servlet Filters to handle login requests by Bearer Authentication using
JSON Web Tokens (JWT), and verify Authorizations for all endpoints except Create and Login API endpoints.

## Testing

Unit tests is implemented demonstrating 86% code coverage.

### Stack

- JUnit 5
- Mockito
- MockMVC

### Total Coverage

|Classes, %|Lines, %|
|:---:|:---:|
|86%|82%|

### Scope

|Element|Class, %|Method, %|Line, %|Branch, %|
|:---:|:---:|:---:|:---:|:---:|
|`com.udacity.ecommerce.controllers`|100% (4/4)|100% (18/18)|100% (106/106)|91% (11/12)
|`com.udacity.ecommerce.handlers`|50% (1/2)|25% (1/4)|16% (2/12)|100% (0/0)
|`com.udacity.ecommerce.model`|92% (13/14)|85% (64/75)|82% (88/107)|0% (0/20)
|`com.udacity.ecommerce.security`|75% (6/8)|83% (15/18)|73% (68/92)|12% (1/8)
|`com.udacity.ecommerce.service`|100% (1/1)|50% (1/2)|20% (1/5)|100% (0/0)

## License

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)