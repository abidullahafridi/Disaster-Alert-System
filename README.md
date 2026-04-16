🚨 Disaster Alert System
A robust backend application built with Java Spring Boot to manage and broadcast emergency alerts. This system is designed to help communities stay informed during disasters by providing timely notifications and resource management.

🛠️ Tech Stack
Framework: Spring Boot (Java)
Build Tool: Maven
Database: MySQL
Other: Hibernate/JPA, RESTful APIs
✨ Key Features
Broadcast emergency alerts to registered users.
CRUD operations for disaster incidents.
User role management (Admin, Volunteer).
Secure REST API endpoints.
🚀 Getting Started (How to Run)
Follow these steps to run the project on your local machine.

1. Prerequisites
Make sure you have the following installed:

Java JDK 17+ (or your specific version)
Maven
MySQL Server
2. Database Setup
A complete SQL script is provided to set up the database structure and dummy data.

Open your MySQL client (e.g., MySQL Workbench, phpMyAdmin).
Create a new database: CREATE DATABASE disaster_db;
Import the database.sql file located in the /database folder of this repository.
3. Application Configuration
Connect the application to your local database.

Open src/main/resources/application.properties.
Update the database credentials to match your local MySQL setup:
spring.datasource.url=jdbc:mysql://localhost:3306/disaster_db
spring.datasource.username=root
spring.datasource.password=YOUR_LOCAL_PASSWORD
