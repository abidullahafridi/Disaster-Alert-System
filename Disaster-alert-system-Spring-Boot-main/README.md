# 🚨 Disaster Alert System

A robust backend application built with Java Spring Boot to manage and broadcast emergency alerts. This system is designed to help communities stay informed during disasters by providing timely notifications and resource management.

## 🛠️ Tech Stack
* **Framework:** Spring Boot (Java)
* **Build Tool:** Maven
* **Database:** MySQL
* **Other:** Hibernate/JPA, RESTful APIs

## ✨ Key Features
* Broadcast emergency alerts to registered users.
* CRUD operations for disaster incidents.
* User role management (Admin, Volunteer).
* Secure REST API endpoints.

---

## 🚀 Getting Started (How to Run)

Follow these steps to run the project on your local machine.

### 1. Prerequisites
Make sure you have the following installed:
* [Java JDK 17+](https://www.oracle.com/java/technologies/javase-downloads.html) (or your specific version)
* [Maven](https://maven.apache.org/download.cgi)
* [MySQL Server](https://dev.mysql.com/downloads/mysql/)

### 2. Database Setup
A complete SQL script is provided to set up the database structure and dummy data.
1. Open your MySQL client (e.g., MySQL Workbench, phpMyAdmin).
2. Create a new database: `CREATE DATABASE disaster_db;`
3. Import the `database.sql` file located in the `/database` folder of this repository.

### 3. Application Configuration
Connect the application to your local database.
1. Open `src/main/resources/application.properties`.
2. Update the database credentials to match your local MySQL setup:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/disaster_db
   spring.datasource.username=root
   spring.datasource.password=YOUR_LOCAL_PASSWORD
