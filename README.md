# 🏋️ AI Fitness Tracker – Microservices Application

A **production-style, microservice-based fitness application** where users log their workouts and receive **AI-generated recommendations** for each activity. Built with **Spring Boot, Spring Cloud, Kafka, MongoDB, Keycloak and React**, and powered by **Google Gemini** through **Spring AI**.

> Log an activity → it is published to Kafka → the AI service analyses it → you get a personalised analysis, improvements, suggestions and safety guidelines.

---

## 📌 Table of Contents

- [Features](#-features)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Microservices Overview](#-microservices-overview)
- [Workflow](#-end-to-end-workflow)
- [Security](#-security-oauth2--keycloak)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Screenshots](#-screenshots)
- [Key Learnings](#-key-learnings)
- [Future Improvements](#-future-improvements)
- [Author](#-author)

---

## ✨ Features

- 🔐 **Secure authentication** with Keycloak (OAuth2 + PKCE)
- 🏃 **Track activities** such as Walking, Running, Swimming, Dancing and more
- ⏱️ Record **duration (minutes)** and **calories burned** for every activity
- 🤖 **AI-generated recommendations** for each activity:
  - 📊 Analysis of the workout
  - 📈 Improvements
  - 💡 Suggestions
  - 🦺 Safety guidelines
- 📨 **Asynchronous processing** using Apache Kafka (producer/consumer)
- 🧭 **Service discovery** with Eureka and a single entry point via API Gateway
- ⚙️ **Centralised configuration** with Spring Cloud Config Server
- 🎨 Responsive **React** frontend with protected routes

---

## 🏗️ Architecture

```
                         ┌────────────────────┐
                         │   React Frontend   │
                         └─────────┬──────────┘
                                   │  (JWT / OAuth2 PKCE)
                         ┌─────────▼──────────┐        ┌──────────────┐
                         │    API Gateway     │◄──────►│   Keycloak   │
                         │ (Spring Cloud GW)  │        │ (Identity)   │
                         └───┬────────────┬───┘        └──────────────┘
                             │            │
              ┌──────────────▼───┐    ┌───▼──────────────┐
              │   User Service   │    │ Activity Service │
              │  (JPA + SQL DB)  │    │    (MongoDB)     │
              └──────────────────┘    └───────┬──────────┘
                                              │ publishes activity event
                                      ┌───────▼────────┐
                                      │  Apache Kafka  │
                                      └───────┬────────┘
                                              │ consumes
                                      ┌───────▼────────┐        ┌───────────────┐
                                      │   AI Service   │◄──────►│ Google Gemini │
                                      │  (Spring AI)   │        └───────────────┘
                                      └───────┬────────┘
                                              │ stores recommendations
                                           MongoDB

   ┌─────────────────┐              ┌──────────────────────┐
   │  Eureka Server  │              │ Config Server (Git)  │
   │ (Discovery)     │              │ Centralised configs  │
   └─────────────────┘              └──────────────────────┘
```

---

## 🧰 Tech Stack

| Layer | Technologies |
|-------|--------------|
| **Backend** | Java, Spring Boot, Spring Data JPA, Spring Data MongoDB |
| **Microservices** | Spring Cloud (Eureka, Config Server, Gateway, OpenFeign / WebClient) |
| **Messaging** | Apache Kafka |
| **AI** | Google Gemini |
| **Security** | Keycloak, OAuth2, OpenID Connect, PKCE, JWT |
| **Databases** | SQL database (User Service), MongoDB (Activity & AI Services) |
| **Frontend** | React, React Router, Axios, Keycloak |
| **Build Tools** | Maven, npm |

---

## 🧩 Microservices Overview

| Service | Responsibility | Database |
|---------|----------------|----------|
| **User Service** | User registration, profile management, syncing users with Keycloak | SQL (JPA/Hibernate) |
| **Activity Service** | Create and fetch user activities, publish activity events to Kafka | MongoDB |
| **AI Service** | Consume activity events, call Gemini, generate and store recommendations | MongoDB |
| **API Gateway** | Single entry point, routing, JWT validation | – |
| **Eureka Server** | Service registry and discovery | – |
| **Config Server** | Centralised external configuration for all services | – |
| **Keycloak** | Identity provider, OAuth2 / OIDC authentication | – |
| **Frontend** | UI for login, adding activities, viewing details and AI recommendations | – |
---

## 🔄 End-to-End Workflow

1. User logs in through **Keycloak** (Authorization Code flow with PKCE).
2. React app sends requests with a **JWT** to the **API Gateway**.
3. Gateway validates the token and routes the request via **Eureka** discovery.
4. User adds an activity (type, duration, calories) → **Activity Service** saves it to MongoDB.
5. Activity Service **publishes an event to Kafka**.
6. **AI Service** consumes the event and prompts **Google Gemini**.
7. The AI response is parsed into **Analysis, Improvements, Suggestions and Safety Guidelines**, then stored.
8. The frontend fetches and displays the recommendation on the **Activity Detail page**.
## 🔐 Security (OAuth2 + Keycloak)

- Keycloak acts as the **Identity Provider**
- Frontend is registered as a **public client using PKCE**
- API Gateway acts as a **resource server** validating JWT tokens
- Keycloak users are **synced with the User Service** so the Keycloak ID maps to the application user

---
