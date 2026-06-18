# 🛒 Enterprise Product & Category Management API (with JWT, Testing & Docker)

👤 **Developed by:** **Ahmad Fawad Amiri** 🔗 **GitHub:** [@AhmadFawadAmiri](https://github.com/AhmadFawadAmiri)  
💼 **Role:** Backend Engineer (Java / Spring Boot)

---

A robust, production-ready RESTful API built with **Spring Boot 3.x** and **Spring Data JPA** utilizing a clean 3-tier architecture. This project has evolved from a basic CRUD service into a secure, scalable, fully unit-tested, and **fully containerized (Dockerized)** backend baseline that handles complex database relationships while ensuring strict architectural decoupling.

---

## 💎 Key Features

* **🔒 Stateless Security (JWT):** Integrated with Spring Security using JSON Web Tokens (JWT) for secure, stateless authentication and Role-Based Access Control (RBAC).
* **🐳 Fully Containerized (Docker & Compose):** Multi-container setup utilizing Docker Healthchecks to guarantee the Spring Boot application only fires up *after* the MySQL container is fully operational, healthy, and ready to accept connections.
* **🏗️ Clean 3-Tier Layered Architecture:** Strict separation of concerns across Web (Controllers), Business (Services), and Data Access (Repositories) layers.
* **📦 Advanced DTO Pattern:** Decouples Database Entities from the presentation layer using specialized payload objects (`ProductCreateRequest`, `ProductDto`) to protect schema integrity and eliminate infinite serialization loops.
* **🛡️ Robust Many-to-Many Relationships:** Smart handling of self-healing `Product <-> Tag` relationships—automatically fetching existing tags or persisting new ones on the fly without database duplicates.
* **📑 Pagination & Sorting:** Optimized database querying using Spring Data JPA `Pageable` (`pageNo`, `pageSize`, `sortBy`) to handle large datasets efficiently.
* **🎯 Global Exception Handling:** A centralized `@RestControllerAdvice` that intercepts low-level system, validation, or business exceptions and translates them into predictable, frontend-friendly JSON contracts.
* **🧪 Mockito-Powered Unit Testing:** Core business services are strictly guarded with isolated unit tests using Mockito for dependency mocking.

---

## 🛠️ Tech Stack & Tools

* **Backend Framework:** Java 21, Spring Boot 3.x
* **Security:** Spring Security, Nimbus-JWT / jjwt
* **Data Access:** Spring Data JPA (Hibernate)
* **Database:** MySQL 8.0 (Dockerized) / H2 (In-Memory Database)
* **Validation:** Jakarta Validation API (`@NotBlank`, `@Min`, `@Size`)
* **Build Tool:** Maven
* **Testing:** JUnit 5, Mockito, DBeaver, Postman

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
```json
{
  "name": "Gaming Laptop",
  "price": 1500.0,
  "categoryId": 1,
  "tags": ["computer", "hardware", "gaming"]
}
```
#### 📦 Sample Response Body (200 OK)
```json
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

```json
{
"status": 400,
"error": "Validation Error",
"message": "Validation failed: [name: The name should be at least 2 Characters.]",
"timestamp": "2026-06-18T18:24:11.042"
}
```

## 🧪 Testing Coverage
The core business logic is monitored and guarded against regressions. You can execute the test suite locally via Maven:

```Bash

mvn test
```

### Sample Service Test (ProductServiceTest):

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

## 🚀 How to Run Locally (The Modern Docker Way)
The entire ecosystem (Application + Database) is fully containerized. You do not need to have MySQL installed on your host machine.

### Execution Commands (Step-by-Step)
Open your terminal in the project root directory and run:

* **1.** Package the Application:
Compile the Java code and generate a fresh production .jar file:

```Bash

./mvnw clean package -DskipTests
```
* **2.** Clear Stale Container Environments (Optional but Recommended):

```Bash

docker compose down -v
```
* **3.** Launch the Integrated System:

```Bash

docker compose up --build
```
The application will be live and breathing at: http://localhost:8080

### ⚙️ Alternative Way: Legacy Local Run (Without Docker)

1. Clone the repository:
``` Bash

git clone https://github.com/AhmadFawadAmiri/demo0001.git
```
2. Navigate to the project directory:

```Bash

cd demo0001
```
3. Configure your database settings in src/main/resources/application.properties.

4. Build and package the application using Maven:

```Bash

mvn clean package
```
5. Run the executable JAR file:
```Bash

java -jar target/demo0001-0.0.1-SNAPSHOT.jar
```
The server will start on http://localhost:8080.

---

## 🗄️ Database Management & Remote Access (Docker)
The MySQL database runs isolated inside Docker, but its port is mapped to your host machine so you can connect via tools like DBeaver or IntelliJ Database Tool.

* Host: localhost

* Port: 3307 (Mapped from internal 3306 to prevent host port conflicts)

* Database Name: shop_db

* Username: root

* Password: fawad

 💡 **Client Tip:** Ensure your DB client has allowPublicKeyRetrieval=true and useSSL=false enabled in its driver properties to connect seamlessly.

---

## 🛠️ Maven Parent Overrides Hint
Due to Maven's design, elements are inherited from the parent POM to the project POM. While most of the inheritance is fine, it also inherits unwanted elements like <license> and <developers> from the parent.

To prevent this, the project POM contains empty overrides for these elements. If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

---

## 📚 Reference Documentation & Guides
For further framework reference, please consider the following official sections:

* Official Apache Maven documentation

* Spring Boot Maven Plugin Reference Guide

* Create an OCI image Guide

* Spring Web Servlet Docs

* Building a RESTful Web Service

* Serving Web Content with Spring MVC

* Building REST services with Spring Tutorial
---

## 🎓 Advanced Learning Outcomes
By evolving this project, the following senior-level paradigms were mastered:

* **Stateless Token-Based Authentication:** Overcoming session-based limitations by implementing a secure JWT filter chain.

* **Containerized Infrastructure & Healthchecks:** Structuring orchestration files to ensure proper database dependency loading and zero startup failures.

* **Advanced Relationship Lifecycle Handling:** Managing complex ManyToMany cascading states cleanly within @Transactional boundaries.

* **Decoupling Architecture:** Understanding why raw JPA Entities should never cross the controller boundary, adopting the Request/Response DTO pattern instead.

* **Mock-Driven Testing:** Writing clean Unit Tests using Mockito to isolate the service layer from actual database connectivity.

---

## 👥 Author & Contact

This project was designed, developed, and containerized from scratch by:
- **Name:** Ahmad Fawad Amiri
- **GitHub:** [github.com/AhmadFawadAmiri](https://github.com/AhmadFawadAmiri)
- **LinkedIn:** *[Ahmad Fawad Amiri](https://linkedin.com/in/ahmad-fawad-amiri-5537a922b/)*
- **LinkedIn:** [![LinkedIn](https://img.shields.shields.shields.shields.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/ahmad-fawad-amiri-5537a922b/)
- **LinkedIn:** [![LinkedIn](https://img.shields.shields.shields.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/ahmad-fawad-amiri-5537a922b/)


Feel free to reach out for collaboration, code reviews, or job opportunities!