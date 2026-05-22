# Development of an AI-Powered Web Application for LinkedIn Content Generation

This repository contains the MVP implementation for a VMU Bachelor thesis project: an AI-powered web application that helps users generate LinkedIn post drafts.

## Technology Stack

- Backend: Java, Spring Boot, Maven
- Frontend: React, Vite, JavaScript, CSS
- Database: configured in the Spring Boot backend
- AI integration: backend calls a Python AI API for content generation
- Python AI API source: included in `/python-api` for documentation and thesis source-code reference

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

## Deployment

Backend deployment on Render or Railway:

```bash
cd backend
./mvnw clean package -DskipTests
java -Dserver.port=$PORT -jar target/linkedin-content-backend-0.0.1-SNAPSHOT.jar
```

Backend environment variables:

```text
AI_PYTHON_BASE_URL=https://linkedin-generator-api.onrender.com
AI_PYTHON_GENERATE_PATH=/api/ai/generate-linkedin-post
FRONTEND_ALLOWED_ORIGINS=https://your-frontend.vercel.app,http://localhost:5173
SPRING_H2_CONSOLE_ENABLED=false
```

The backend runs locally on port `15150` by default and uses the cloud provider `PORT` value when it is available. To enable the H2 console locally, set `SPRING_H2_CONSOLE_ENABLED=true` before starting the backend.

Backend Docker deployment on Render:

```text
Root Directory: backend
Environment: Docker
Dockerfile path: Dockerfile
```

If Render is configured with the repository root as the root directory instead, use:

```text
Dockerfile path: backend/Dockerfile
```

Required backend environment variables for Render Docker deployment:

```text
AI_PYTHON_BASE_URL=https://linkedin-generator-api.onrender.com
AI_PYTHON_GENERATE_PATH=/api/ai/generate-linkedin-post
SPRING_H2_CONSOLE_ENABLED=false
FRONTEND_ALLOWED_ORIGINS=http://localhost:5173
```

Frontend deployment on Vercel:

```text
Root directory: frontend
Build command: npm run build
Output directory: dist
```

Set this Vercel environment variable to the deployed backend URL:

```text
VITE_API_BASE_URL=https://your-backend-url
```

The `/python-api` folder is included only as source-code reference for the thesis. It is already deployed separately on Render and is not deployed from this repository.

## Python AI API Note

The `/python-api` folder contains the external FastAPI AI service source code for documentation and thesis review purposes. The service is already deployed separately on Render, and the Java backend calls the deployed Python AI API URL during normal MVP usage.

## MVP Scope

This app generates LinkedIn post drafts only. It does not publish, schedule, analyze, or use LinkedIn API.

## Dummy Login

The login screen is frontend-only demo login for MVP purposes.

