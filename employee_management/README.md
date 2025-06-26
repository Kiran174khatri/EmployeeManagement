#  Employee Management System - Spring Boot Project

This is a secure **Employee Management System** built with **Spring Boot**, using **JWT for authentication**, **Role-based access control**, **PostgreSQL** for the database, and **Flyway** for database versioning. It also includes a fully functional **Swagger UI** and a **Postman collection** for easy API testing.

---

##  Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Kiran174khatri/EmployeeManagement.git
cd employee_management
```
---
### Directory tree
```text
employee_management/
├── src/
├── postman/
│   └── Employee-Management-API.postman_collection.json
├── src/main/resources/db/migration/
│   └── V1__create_roles_users_employees.sql
├── application.yaml
├── README.md
└── pom.xml
```
---
### 2. Set Up PostgreSQL
- Create a database manually named: **employeeskiran**

- Make sure your DB is running on default port 5432

```bash
CREATE DATABASE employeeskiran;
```

---
### 3. Configure application.yaml
```bash
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/employeeskiran
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver

```
---
### 4. Liquibase 
- Liquibase allows us to manage database changes such as inserting default records (e.g., admin/user) without manually    running SQL files every time.

- Step 1: Keep Liquibase disabled for the first run
- Step 2: Run the Spring Boot application
- Step 3: Enable Liquibase to insert default users
```bash
spring:
  liquibase:
    enabled: true  
```
- Step 4: Re-run the Spring Boot Application

---



### 5. Build and Run

```bash
./mvnw spring-boot:run
```
---
## Postman Collection

Use the Postman collection to test the API.

To use:
1. Open Postman.
2. Click `Import`.
3. Choose the `.json` file from the `postman` folder in the project directory.
4. Use the collection to test all endpoints.
---
## Authentication & Roles

### Register Users (via auth/register)
- Password is encoded using BCrypt

- Role is either ADMIN or USER

### Login (via auth/login)
- Returns a JWT token
- Use this token for authenticated requests
---
## Swagger API Docs
You can Authorize using the JWT token here.
```bash
http://localhost:8080/swagger-ui.html
```

---
### To generate the token
Open **POSTMAN**
- Login Endpoint
- URL: /auth/login
- Method: POST
- Content-Type: application/json

For Admin
```bash
{
  "username": "admin",
  "password": "admin123"
}
```
For User
```bash
{
  "username": "user",
  "password": "user123"
}
```
You will get token in response.

---
## API Endpoints


| Endpoint             | Method | Role Required | Description                          |
|----------------------|--------|----------------|--------------------------------------|
| `/auth/register`     | POST   | Public         | Register a new user                  |
| `/auth/login`        | POST   | Public         | Login and receive JWT                |
| `/employees`         | GET    | ADMIN, USER    | Get all employees                    |
| `/employees`         | POST   | ADMIN          | Add new employee                     |
| `/employees/{id}`    | PUT    | ADMIN          | Update employee                      |
| `/employees/{id}`    | DELETE | ADMIN          | Delete employee                      |
| `/employees/avg`     | GET    | ADMIN          | Get employees with above average salary |
| `/employees/count`   | GET    | ADMIN          | Get employee count by department     |
