#  Automated Task Manager

## Overview

The Automated Task Manager is a full-stack web application that allows users to efficiently create, manage, and track tasks. It is designed with a focus on **secure authentication**, **scalable backend architecture**, and **real-time user experience**.


## Tech Stack

* Frontend: React.js
* Backend: Spring Boot (REST APIs)
* Database: MySQL
* Authentication: JWT (JSON Web Token)


## Architecture

The application follows a layered architecture:

Frontend (React) → REST APIs → Spring Boot Backend → MySQL Database

* Controller Layer: Handles HTTP requests
* Service Layer: Contains business logic
* Repository Layer: Interacts with the database


## Authentication Flow (JWT)

1. User logs in with credentials
2. Backend validates user and generates JWT token
3. Token is sent to the client and stored
4. Client includes token in Authorization header for every request
5. Backend validates token before granting access

Ensures secure and stateless authentication


## Database Design

### Users Table

* user_id (Primary Key)
* email
* password

### Tasks Table

* task_id (Primary Key)
* title
* description
* status
* user_id (Foreign Key)

### Relationship

* One user → Multiple tasks (One-to-Many)


##  Key Features

* Secure user authentication using JWT
* Create, update, delete tasks (CRUD operations)
* Task status tracking
* Responsive UI using React
* RESTful API design


## How to Run

### Backend

1. Navigate to backend folder
2. Run Spring Boot application
3. Configure MySQL in application.properties

### Frontend

1. Navigate to frontend folder
2. Run:

   ```bash
   npm install
   npm start
   ```


## Challenges Faced

* Implementing secure authentication using JWT
* Handling real-time updates efficiently
* Designing proper database relationships


## Key Learnings

* Implemented stateless authentication using JWT
* Designed scalable REST APIs using Spring Boot
* Structured relational database using MySQL
* Integrated frontend and backend communication


## Future Improvements

* Add caching (Redis) for performance optimization
* Implement role-based access control
* Improve scalability using microservices

## Why this Project?

This project demonstrates my ability to build a **secure, scalable full-stack application** using modern technologies and best practices.

---
