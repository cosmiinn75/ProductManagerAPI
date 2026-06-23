# Spring Security Product API

A Spring Boot backend learning project built to practice REST APIs, authentication, authorization, DTOs, validation, global exception handling, role/permission-based access control, admin operations, and MySQL database integration.

The application allows users to register, log in, receive a JWT token, and manage their own products through protected endpoints. It also includes admin-only endpoints for user management and role updates.

---

## Features

- User registration
- User login
- Password hashing with BCrypt
- JWT-based authentication
- Stateless authentication with Spring Security
- Role and permission-based authorization
- User-owned product CRUD operations
- Admin-only user management
- Admin-only role update endpoint
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
- Admin-only endpoints
- Entity relationships
- DTO mapping
- Request validation
- Custom exceptions
- Global exception handling
- User-specific resources
- Ownership checks for protected resources

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
| GET | `/api/products` | Get all products owned by the authenticated user | Authenticated user |
| POST | `/api/products` | Create a new product for the authenticated user | Authenticated user |
| PUT | `/api/products/{id}` | Update an owned product | Authenticated user |
| DELETE | `/api/products/{id}` | Delete an owned product | Authenticated user |

### Admin

| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/admin/users` | Get all users | Admin |
| PUT | `/api/admin/users/{id}` | Update a user's username and name | Admin |
| PUT | `/api/admin/users/{id}/role` | Update a user's role | Admin |

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

### Update User

```json
{
  "username": "newUsername",
  "name": "New Name"
}
```

### User Response

```json
{
  "id": 1,
  "username": "cosmin",
  "name": "Cosmin",
  "role": "CUSTOMER"
}
```

### Update User Role

```json
{
  "role": "ADMIN"
}
```

Allowed role values:

```text
CUSTOMER
ADMIN
```

---

## Authentication Flow

1. The user registers with a username, name, and password.
2. The password is hashed using BCrypt before being saved in the database.
3. The user logs in with username and password.
4. If the credentials are valid, the backend generates a JWT token.
5. The client sends the JWT token in the `Authorization` header for protected requests.
6. The JWT filter validates the token and extracts the user's role.
7. The role is used to generate authorities based on permissions.
8. The authenticated user is stored in the Spring Security context.
9. The user can access protected endpoints based on their role and permissions.

Example authorization header:

```text
Authorization: Bearer <token>
```

---

## Roles and Permissions

The project uses roles and permissions to protect endpoints.

Example product permissions:

- `READ_ALL_PRODUCTS`
- `SAVE_ONE_PRODUCT`
- `UPDATE_ONE_PRODUCT`
- `DELETE_ONE_PRODUCT`

Example admin permissions:

- `READ_ALL_USERS`
- `UPDATE_ONE_USER`
- `UPDATE_USER_ROLE`

Regular users can manage their own products.

Admin users can access admin-only endpoints, view all users, update user information, and update user roles.

---

## Ownership Rules

Products are linked to the authenticated user.

A user can only update or delete products that belong to their own account.

Example:

```text
User A creates Product 1.
User B cannot update or delete Product 1.
```

This ownership check is handled in the service layer.

---

## DTOs

The project uses DTOs to separate API input/output from database entities.

DTOs used in this project:

- `RegisterRequest`
- `AuthRequest`
- `AuthResponse`
- `ProductRequest`
- `ProductResponse`
- `UserRequest`
- `UserResponse`
- `RoleRequest`

Using DTOs helps prevent exposing unnecessary internal data, such as passwords or full entity information.

Example product flow:

```text
ProductRequest -> Product entity -> ProductResponse
```

Example auth flow:

```text
AuthRequest -> authentication logic -> AuthResponse
```

Example admin user flow:

```text
UserRequest -> User entity -> UserResponse
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
- Invalid role
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

Example invalid role response:

```json
{
  "error": "Bad request",
  "message": "Invalid role"
}
```

---

## Security Notes

Some authorization errors are handled directly by Spring Security before the request reaches the controller or service layer.

For example:

```text
A CUSTOMER trying to access /api/admin/users will receive 403 Forbidden.
```

This happens because Spring Security checks permissions before the request reaches the admin service methods.

Custom Spring Security error handlers for 401 and 403 responses can be added as a future improvement.

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
9. Test ownership restrictions
10. Test unauthorized and forbidden requests
11. Change a user to ADMIN in the database or through the role update endpoint
12. Test admin-only endpoints

---

## Example Postman Authorization

For protected endpoints, use:

```text
Authorization Type: Bearer Token
Token: <your-jwt-token>
```

---

## Admin Testing Flow

1. Register a normal user.
2. Login and get the JWT token.
3. Try to access:

```text
GET /api/admin/users
```

A normal user should receive:

```text
403 Forbidden
```

4. Change the user's role to `ADMIN`.
5. Login again to receive a new JWT token.
6. Use the new token to access:

```text
GET /api/admin/users
```

The admin user should now be able to view all users.

---

## Project Status

This is a backend learning project built to practice Spring Boot, Spring Security, JWT authentication, DTOs, validation, global exception handling, database relationships, user-owned resources, and admin authorization.

The project is not intended to be production-ready, but it demonstrates important backend concepts used in real-world applications.

---

## Future Improvements

Possible future improvements:

- Add refresh tokens
- Add pagination for products and users
- Add unit and integration tests
- Add Docker support
- Add Swagger/OpenAPI documentation
- Add custom handlers for Spring Security 401 and 403 responses
- Add audit logs for admin actions
- Add a frontend or connect it to a Unity game client
