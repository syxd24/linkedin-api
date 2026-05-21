# Development of an AI-Powered Web Application for LinkedIn Content Generation

This repository contains the MVP implementation for a VMU Bachelor thesis project: an AI-powered web application that helps users generate LinkedIn post drafts.

## Technology Stack

- Backend: Java, Spring Boot, Maven
- Frontend: React, Vite, JavaScript, CSS
- Database: configured in the Spring Boot backend
- AI integration: backend calls a Python AI API for content generation

## Backend Setup

Backend URL: http://localhost:15150

```powershell
cd backend
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

The Java backend receives generation requests, calls the Python AI API, and stores generated content in the database.

## Frontend Setup

Frontend URL: http://localhost:5173

```powershell
cd frontend
npm install
npm run dev
```

To create a production build:

```powershell
npm run build
```

## Python AI API Note

The backend expects a Python AI API service to be available for generating LinkedIn post draft content. Start and configure the Python AI API according to the local thesis/demo environment before using generation features.

## MVP Scope

This app generates LinkedIn post drafts only. It does not publish, schedule, analyze, or use LinkedIn API.

## Dummy Login

The login screen is frontend-only demo login for MVP purposes.
