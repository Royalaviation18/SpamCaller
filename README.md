
# Spam-Caller

The app enables users to identify spam numbers, search for individuals by phone number or name, and manage user profiles. The API is built with production-level considerations for security, performance, and scalability.

## Authors

- [@Royalaviation18](https://github.com/Royalaviation18)


## Tech Stack

**Backend Framework:** Spring Boot

**Programming Language:** Java

**Database:** MySQL (or other supported relational databases)

**ORM:** Hibernate (via Spring Data JPA)

**Build Tool:** Maven

**REST API Framework:** Spring MVC


## Features
    
1. User Management

- Registration: Users can register with their name, phone number, password, and email address. Each phone number must be unique.

- Authentication: Users must log in to access any functionality.

- Profile Management: Users can view and update their profiles.

2. Contacts Management

- Users' personal contacts are automatically imported into the global database (assumed to be pre-imported for this task).

3. Spam Management

- Users can mark a number as spam, even if it does not belong to a registered user or contact.

4. Search Functionality

- By Name: Search the global database for people by name. Results are prioritized as follows:

- Names starting with the query.

- Names containing the query.

By Phone Number: Search for a person by phone number. Results include:

- Registered users with the exact phone number.

- Non-registered entries matching the phone number.

- The detailed view includes name, phone number, spam likelihood, and optionally email (if conditions are met).
## Installation

Before running the application, ensure that you have the following installed:

1. Java 11+: Spring Boot is compatible with Java 11 and later.

2. MySQL Database: The application uses MySQL as the database. Make sure you have MySQL installed and running.
## Run Locally

1. Clone the Repository

```bash
  git clone https://github.com/Royalaviation18/SpamCaller.git
  cd SpamCaller
```
2. Set Up the Database:
- Create a new database in MySQL:

```bash
CREATE DATABASE cSystem;
```

3. Configure Database Connection 
- Update the database connection details in: 
   **src/main/resources/application.properties:**

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/cSystem #epresents the port number/database name
spring.datasource.username=root  # Replace with your MySQL username
spring.datasource.password=root  # Replace with your MySQL password
```
4. Build and Run the Application:

```bash
mvn spring-boot:run
```
**Or run the SpamCallerApplication.java file in an IDE like IntelliJ IDEA or Eclipse**.

5. Access the API:
Once the application is running, access the endpoints via Postman or a similar tool.
    
## API Reference

**Public Endpoints**

#### Register

```http
 POST http://localhost:8080/register
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `localhost:8080` | `NA` | **localhost:8080** refers to the local machine running the server and 8080 is the port on which the server is listening |

- Example Request Body

```bash
{
  "name": "John Doe",
  "phoneNumber": "1234567800",
  "email": "johndoe@example.com",
  "password": "password123",
  "contacts": [
    {
      "name": "Jane Smith",
      "phoneNumber": "0987654321",
      "email": "janesmith@example.com"
    }
  ]
}
```

#### Login

```http
  POST http://localhost:8080/login
```
**Use similar body as the registration request to log in**

### Protected Endpoints (Requires JWT Token)

#### Mark Spam

```http
  PUT http://localhost:8080/markSpam?phoneNumber=:phoneNumber
```


| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `:phoneNumber` | `String` | **phoneNumber**  to be marked as spam |


#### Search by Name

```http
  GET http://localhost:8080/search?name=:name
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `:name` | `String` |  **name** to be searched |


#### Search by Phone Number

```http
GET http://localhost:8080/searchByPhoneNumber?phoneNumber=:phoneNumber
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `:phoneNumber` | `String` |  **Phone number** to be searched |


#### Update User

```http
  PUT http://localhost:8080/updateUser/:id
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `:id` | `String` |  **User ID** to be updated |


#### Soft Delete User

```http
   PUT http://localhost:8080/delete?phoneNumber=:phoneNumber
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `:phoneNumber` | `String` |  **Phone number** of the user to be deleted |








## Swagger Interaction

The project includes Swagger for API documentation and testing. Swagger UI provides an interactive interface to explore and interact with the API endpoints.

Accessing Swagger UI

Start the application by running:

```bash
mvn spring-boot:run
```

Open your browser and navigate to:

```bash
   http://localhost:8080/swagger-ui.html
```

Use the Swagger UI to:

- Explore available API endpoints.

- Test API functionality with predefined request bodies.

- View detailed documentation for each endpoint, including request parameters and response formats.
## Screenshot

![App Screenshot](https://github.com/Royalaviation18/SpamCaller/blob/main/.mvn/wrapper/Screenshots/image.png)


## Contributing
Contributions are always welcome! Please follow these steps to contribute:

- Fork the repository.

- Create a new branch for your feature or bug fix.

- Commit your changes with clear messages.

- Open a pull request to the dev branch.



## License

This project is licensed under the MIT License. See the [LICENSE](https://choosealicense.com/licenses/mit/) file for details.