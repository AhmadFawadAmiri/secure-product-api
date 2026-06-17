# Product & Category Management API

A robust, production-ready RESTful API built with **Spring Boot** and **Spring Data JPA** utilizing a clean 3-tier architecture. This project demonstrates how to handle `Many-to-One` and `One-to-Many` database relationships efficiently while avoiding infinite serialization loops using the **DTO (Data Transfer Object) pattern**.

---

## 🚀 Key Features

* **Complete CRUD Operations:** Fully implemented Create, Read, Update, and Delete endpoints for both Products and Categories.
* **DTO Pattern:** Decoupled Database Entities from the API Presentation Layer to optimize JSON responses and prevent infinite recursion loops.
* **Global Exception Handling:** Centralized error management system transforming messy stack traces into user-friendly JSON responses with correct HTTP status codes.
* **Data Validation:** Strict server-side validation using Jakarta Validation (`@NotBlank`, `@Min`, `@Size`).
* **Transactional Safety:** Ensured database integrity during multi-step operations using Spring's `@Transactional`.

---

## 🛠️ Tech Stack & Tools

* **Backend:** Java 17+, Spring Boot 3.x
* **Data Access:** Spring Data JPA (Hibernate)
* **Database:** MySQL / H2 (In-Memory Database)
* **Build Tool:** Maven
* **Testing:** Postman

---

## 📂 Project Architecture

The project follows the standard **3-Tier Architecture** for clean separation of concerns:

```text
src/main/java/com/afa/demo0001/
├── controller/   # Web Layer (Exposes REST Endpoints)
├── service/      # Business Logic Layer (Handles Transactions)
├── repository/   # Data Access Layer (Interacts with Database)
├── model/        # Database Entities (JPA Mapping & Validation)
├── dto/          # Data Transfer Objects (Request/Response Payloads)
└── exception/    # Global Exception Handler
```
---
## 📡 API Endpoints & Usage
### 1. Categories
GET /api/categories - Fetch all categories (Returns CategoryDto).

POST /api/categories - Create a new category.

### 2. Products
GET /api/products - Fetch all products with their associated category names (Returns ProductDto).

POST /api/products - Create a new product.

PUT /api/products/{id} - Update an existing product by ID.

DELETE /api/products/{id} - Delete a product by ID.

---
#### 📝 Sample Request Body (POST /api/products)
```JSON
{
    "name": "Samsung Galaxy S24",
    "price": 1200.0,
    "category": {
        "id": 1
    }
}
```
#### 📦 Sample Response Body (200 OK)
```JSON
{
    "id": 5,
    "name": "Samsung Galaxy S24",
    "price": 1200.0,
    "categoryName": "Electronics"
}
```
---
### ⚙️ How to Run Locally
Clone the repository:
``` Bash

git clone [https://github.com/AhmadFawadAmiri/demo0001.git](https://github.com/AhmadFawadAmiri/demo0001.git)
```
Navigate to the project directory:

```Bash

cd demo0001
```
Configure your database settings in src/main/resources/application.properties.

Build and package the application using Maven:

```Bash

mvn clean package
```
Run the executable JAR file:
```Bash

java -jar target/demo-0.01-SNAPSHOT.jar
```
The server will start on http://localhost:8080.

## 🎓 Learning Outcomes
This project was built to master core backend development concepts, including:

* **Solving Infinite Recursion** (StackOverflowError) caused by bidirectional JPA relationships.

* **Implementing custom validation and catching** MethodArgumentNotValidException.

* **Database debugging and logs** analysis in IntelliJ.