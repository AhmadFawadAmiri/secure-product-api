# 🚀 Enterprise Product & Category Management API (with JWT & Testing)

A robust, production-ready RESTful API built with **Spring Boot 3.x** and **Spring Data JPA** utilizing a clean 3-tier architecture. This project has evolved from a basic CRUD service into a secure, scalable, and fully unit-tested backend baseline that handles complex database relationships while ensuring strict architectural decoupling.

---

## 💎 Key Features

* **🔒 Stateless Security (JWT):** Integrated with Spring Security using JSON Web Tokens (JWT) for secure, stateless authentication and Role-Based Access Control (RBAC).
* **🏗️ Clean 3-Tier Layered Architecture:** Strict separation of concerns across Web (Controllers), Business (Services), and Data Access (Repositories) layers.
* **📦 Advanced DTO Pattern:** Decouples Database Entities from the presentation layer using specialized payload objects (`ProductCreateRequest`, `ProductDto`) to protect schema integrity and eliminate infinite serialization loops.
* **🛡️ Robust Many-to-Many Relationships:** Smart handling of self-healing `Product <-> Tag` relationships—automatically fetching existing tags or persisting new ones on the fly without database duplicates.
* **📑 Pagination & Sorting:** Optimized database querying using Spring Data JPA `Pageable` (`pageNo`, `pageSize`, `sortBy`) to handle large datasets efficiently.
* **🎯 Global Exception Handling:** A centralized `@RestControllerAdvice` that intercepts low-level system, validation, or business exceptions and translates them into predictable, frontend-friendly JSON contracts.
* **🧪 Mockito-Powered Unit Testing:** Core business services are strictly guarded with isolated unit tests using Mockito for dependency mocking.

---

## 🛠️ Tech Stack & Tools

* **Backend Framework:** Java 17+, Spring Boot 3.x
* **Security:** Spring Security, Nimbus-JWT / jjwt
* **Data Access:** Spring Data JPA (Hibernate)
* **Database:** MySQL / H2 (In-Memory Database)
* **Validation:** Jakarta Validation API (`@NotBlank`, `@Min`, `@Size`)
* **Build Tool:** Maven
* **Testing:** JUnit 5, Mockito, Postman

---

## 📂 Project Architecture

```text
src/main/java/com/afa/demo0001/
├── controller/   # Web Layer (Exposes Secure REST Endpoints)
├── service/      # Business Logic Layer (Handles Transactions & Tag Mapping)
├── repository/   # Data Access Layer (Spring Data JPA Repositories)
├── model/        # Database Entities (Product, Category, Tag with JPA Mapping)
├── dto/          # Data Transfer Objects (Request/Response Payloads)
├── security/     # JWT Configuration, Filters, and Custom Deserializers
└── exception/    # Global Exception Handler (@RestControllerAdvice)

```
## 📡 Core API Endpoints Showcase
### 1. Products Layer
   GET /api/products?pageNo=0&pageSize=10&sortBy=price - Fetch paginated and sorted products.

POST /api/products - Create a new product with automated tag binding. (Requires Authentication)

PUT /api/products/{id} - Update an existing product and its tags. (Requires Authentication)

DELETE /api/products/{id} - Delete a product by ID.

### 2. Categories Layer
   GET /api/categories - Fetch all categories.

POST /api/categories - Create a new category.

---
#### 📝 Sample Request Body (POST /api/products)
```JSON
{
  "name": "Gaming Laptop",
  "price": 1500.0,
  "categoryId": 1,
  "tags": ["computer", "hardware", "gaming"]
}
```
#### 📦 Sample Response Body (200 OK)
```JSON
{
  "id": 33,
  "name": "Gaming Laptop",
  "price": 1500.0,
  "categoryName": "Electronics"
}
```
---
#### ⚠️ Standardized Error Contract Sample
If validation fails or an invalid ID is passed, the client receives a reliable JSON contract instead of a messy stack trace:

```JSON
{
"status": 400,
"error": "Validation Error",
"message": "Validation failed: [name: The name should be at least 2 Characters.]",
"timestamp": "2026-06-18T18:24:11.042"
}
```

### 🧪 Testing Coverage
The core business logic is monitored and guarded against regressions. You can execute the test suite locally via Maven:

```Bash

mvn test
```

#### Sample Service Test (ProductServiceTest):

```Java
@Test
public void testSaveProductDto_Success() {
when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));
when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

    ProductDto result = productService.saveProductDto(request);

    assertNotNull(result);
    assertEquals("Test Phone", result.getName());
    verify(productRepository, times(1)).save(any(Product.class));
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

java -jar target/demo0001-0.0.1-SNAPSHOT.jar
```
The server will start on http://localhost:8080.

## 🎓 Advanced Learning Outcomes
By evolving this project, the following senior-level paradigms were mastered:

* **Stateless Token-Based Authentication:** Overcoming session-based limitations by implementing a secure JWT filter chain.

* **Advanced Relationship Lifecycle Handling:** Managing complex ManyToMany cascading states cleanly within @Transactional boundaries.

* **Decoupling Architecture:** Understanding why raw JPA Entities should never cross the controller boundary, adopting the Request/Response DTO pattern instead.

* **Mock-Driven Testing:** Writing clean Unit Tests using Mockito to isolate the service layer from actual database connectivity.

