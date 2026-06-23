# Spring Security Product API

A Spring Boot backend learning project built to practice REST APIs, authentication, authorization, DTOs, validation, global exception handling, and MySQL database integration.

The application allows users to register, log in, receive a JWT token, and manage their own products through protected endpoints.

---

## Features

- User registration
- User login
- Password hashing with BCrypt
- JWT-based authentication
- Role and permission-based authorization
- User-owned product CRUD operations
- DTOs for request and response objects
- Input validation with `@Valid`
- Global exception handling with `@RestControllerAdvice`
- MySQL database integration
- Spring Data JPA
- Protected endpoints using Spring Security

---

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT
- BCrypt
- Maven
- Postman

---

## Main Concepts Practiced

This project was built to practice important backend development concepts such as:

- Controller-Service-Repository architecture
- REST API design
- Authentication and authorization
- JWT generation and validation
- Custom security filters
- Role and permission management
- Entity relationships
- DTO mapping
- Request validation
- Custom exceptions
- Global exception handling
- User-specific resources

---

## API Endpoints

### Authentication

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/auth/register` | Register a new user | Public |
| POST | `/api/auth/login` | Login and receive a JWT token | Public |

### Products

| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/products` | Get all products owned by the authenticated user | Authenticated |
| POST | `/api/products` | Create a new product | Authorized user |
| PUT | `/api/products/{id}` | Update an owned product | Authorized user |
| DELETE | `/api/products/{id}` | Delete an owned product | Authorized user |

---

## Example Requests

### Register

```json
{
  "username": "cosmin",
  "name": "Cosmin",
  "password": "1234"
}
```

### Login

```json
{
  "username": "cosmin",
  "password": "1234"
}
```

### Login Response

```json
{
  "token": "jwt-token-here"
}
```

### Create Product

```json
{
  "name": "Laptop",
  "price": 3000
}
```

### Product Response

```json
{
  "id": 1,
  "name": "Laptop",
  "price": 3000
}
```

---

## Authentication Flow

1. The user registers with a username, name, and password.
2. The password is hashed using BCrypt before being saved in the database.
3. The user logs in with username and password.
4. If the credentials are valid, the backend generates a JWT token.
5. The client sends the JWT token in the `Authorization` header for protected requests.
6. The JWT filter validates the token and sets the authenticated user in the Spring Security context.
7. The user can access protected endpoints based on their role and permissions.

Example authorization header:

```text
Authorization: Bearer <token>
```

---

## Roles and Permissions

The project uses roles and permissions to protect endpoints.

Example permissions:

- `READ_ALL_PRODUCTS`
- `SAVE_ONE_PRODUCT`
- `UPDATE_ONE_PRODUCT`
- `DELETE_ONE_PRODUCT`

A user can only access and modify products that belong to their own account.

---

## DTOs

The project uses DTOs to separate API input/output from database entities.

DTOs used in this project:

- `RegisterRequest`
- `AuthRequest`
- `AuthResponse`
- `ProductRequest`
- `ProductResponse`

Using DTOs helps prevent exposing unnecessary internal data, such as passwords or full user entity information.

Example flow:

```text
ProductRequest -> Product entity -> ProductResponse
```

---

## Validation

The project uses validation annotations such as:

- `@NotBlank`
- `@DecimalMin`
- `@Valid`

Example invalid product request:

```json
{
  "name": "",
  "price": 0
}
```

Example validation response:

```json
{
  "name": "must not be blank",
  "price": "must be greater than or equal to 0.01"
}
```

---

## Global Exception Handling

The project includes a global exception handler using `@RestControllerAdvice`.

Handled errors include:

- Product not found
- User not found
- Access denied
- Invalid credentials
- Existing username
- Validation errors

Example not found response:

```json
{
  "error": "Not found",
  "message": "Product not found!"
}
```

Example conflict response:

```json
{
  "error": "Conflict",
  "message": "Username already exists"
}
```

Example unauthorized response:

```json
{
  "error": "Unauthorized",
  "message": "Invalid username or password"
}
```

Example forbidden response:

```json
{
  "error": "Forbidden",
  "message": "You can't change this product"
}
```

---

## Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/cosmiinn75/spring-security-product-api.git
```

### 2. Open the project in your IDE

You can open the project using IntelliJ IDEA or another Java IDE.

### 3. Configure the MySQL database

Update your `application.properties` file with your local MySQL configuration.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_security_product_api
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Run the application

Run the Spring Boot application from your IDE or using Maven:

```bash
mvn spring-boot:run
```

### 5. Test the API

Use Postman to test the endpoints.

Recommended testing order:

1. Register a user
2. Login and copy the JWT token
3. Use the token as a Bearer Token
4. Create a product
5. Get all products
6. Update a product
7. Delete a product
8. Test validation errors
9. Test unauthorized and forbidden requests

---

## Example Postman Authorization

For protected endpoints, use:

```text
Authorization Type: Bearer Token
Token: <your-jwt-token>
```

---

## Project Status

This is a backend learning project built to practice Spring Boot, Spring Security, JWT authentication, DTOs, validation, global exception handling, and database relationships.

The project is not intended to be production-ready, but it demonstrates important backend concepts used in real-world applications.

---

## Future Improvements

Possible future improvements:

- Add refresh tokens
- Add pagination for products
- Add unit and integration tests
- Add Docker support
- Add Swagger/OpenAPI documentation
- Add custom handlers for Spring Security 401 and 403 responses
- Add a frontend or connect it to a Unity game client
