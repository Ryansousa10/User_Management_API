
![Giphy Animation](https://user-images.githubusercontent.com/74038190/240906093-9be4d344-6782-461a-b5a6-32a07bf7b34e.gif)

# SpringBoot_Challenge03_RyanSousa
Week XII - CompassUOL Challenge

## User Management API

### Overview

This API provides functionality for managing user accounts, including user creation, authentication, and profile updates. Using RabbitMQ an open-source and lightweight message broker which supports multiple messaging protocols.

### Endpoints

#### 1. Create a New User

- **Endpoint:** `/v1/users`
- **Method:** `POST`
- **Description:** Creates a new user based on the provided data.
- **Request:**
  - **Body:**
    - `user` (UserDTO): User data to be created
- **Responses:**
  - `201`: User created successfully
    - Schema: [UserDTO](#userdto)
  - `400`: Invalid request
  - `500`: Internal server error

#### 2. User Authentication

- **Endpoint:** `/v1/login`
- **Method:** `POST`
- **Description:** Authenticates a user based on the provided credentials.
- **Request:**
  - **Body:**
    - `loginRequest` (LoginRequestDTO): Login credentials
- **Responses:**
  - `200`: Login successful
    - Schema: [LoginResponseDTO](#loginresponsedto)
  - `401`: Invalid credentials
  - `500`: Internal server error

#### 3. Get User Information

- **Endpoint:** `/v1/users/{id}`
- **Method:** `GET`
- **Description:** Retrieves information about a user based on the provided ID.
- **Parameters:**
  - `id` (Path Parameter): User ID (integer)
- **Responses:**
  - `200`: User found
    - Schema: [UserDTO](#userdto)
  - `404`: User not found
  - `500`: Internal server error

#### 4. Update User Information

- **Endpoint:** `/v1/users/{id}`
- **Method:** `PUT`
- **Description:** Updates information about a user based on the provided ID.
- **Parameters:**
  - `id` (Path Parameter): User ID (integer)
- **Request:**
  - **Body:**
    - `user` (UserDTO): New user data
- **Responses:**
  - `200`: User updated successfully
    - Schema: [UserDTO](#userdto)
  - `404`: User not found
  - `500`: Internal server error

#### 5. Update User Password

- **Endpoint:** `/v1/users/{id}/password`
- **Method:** `PUT`
- **Description:** Updates the password of a user based on the provided ID.
- **Parameters:**
  - `id` (Path Parameter): User ID (integer)
- **Request:**
  - **Body:**
    - `user` (PasswordUpdateDTO): New user password
- **Responses:**
  - `200`: Password updated successfully
  - `500`: Internal server error

### Data Models

#### UserDTO

```json
{
  "type": "object",
  "properties": {
    "firstName": {"type": "string", "minLength": 3, "maxLength": 50},
    "lastName": {"type": "string", "minLength": 3, "maxLength": 50},
    "email": {"type": "string", "format": "email", "maxLength": 100},
    "cpf": {"type": "string", "maxLength": 14},
    "birthdate": {"type": "string", "format": "date"},
    "password": {"type": "string", "minLength": 6}
  }
}
```

### LoginRequestDTO

```json
{
  "type": "object",
  "properties": {
    "email": {"type": "string", "format": "email"},
    "password": {"type": "string", "minLength": 6}
  }
}
```

### LoginResponseDTO

```json
{
  "type": "object",
  "properties": {
    "token": {"type": "string"}
  }
}
```

### PasswordUpdateDTO

```json
{
  "type": "object",
  "properties": {
    "password": {"type": "string", "minLength": 6}
  }
}
```

### Technologies Used

- **Java 17:** The programming language used for backend development.
- **Spring Boot:** A framework for building Java-based enterprise applications.
- **Spring Security:** Provides authentication and access control for the application.
- **Spring Tests:** Framework for testing Spring-based applications.
- **MySQL:** Relational database management system used for storing user data.
- **MongoDB:** NoSQL database used for storing notification data.
- **IntelliJ:** Integrated Development Environment (IDE) for Java development.
- **RabbitMQ:** Message broker used for communication between microservices.

## Getting Started

To set up and run the project locally, follow these steps:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Ryansousa10/SpringBoot_Challenge03_RyanSousa.git

2. **Install Dependencies:**

Open the project in IntelliJ.
Build the project to download dependencies.
Configure Databases:

3. Set up MySQL and MongoDB databases and update application properties.
Run the Application:

4. Start the application from IntelliJ or using the terminal.
Make Requests:

5. Use your preferred API client (e.g., Postman) to make requests to the specified endpoints.
