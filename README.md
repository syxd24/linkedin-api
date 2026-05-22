# Development of an AI-Powered Web Application for LinkedIn Content Generation

This repository contains the MVP implementation for a VMU Bachelor thesis project: **Development of an AI-Powered Web Application for LinkedIn Content Generation**.

The application helps users generate LinkedIn post drafts using artificial intelligence. Users can enter post details, choose options such as tone and post type, and receive an AI-generated LinkedIn post draft.

## Live Links

Frontend application:  
https://linkedin-api-ochre.vercel.app

Backend health check:  
https://linkedin-thesis-backend.onrender.com/api/health

GitHub repository:  
https://github.com/syxd24/linkedin-api

## Project Overview

The system is an AI-powered web application for generating LinkedIn content drafts.

The user can provide:

- Post topic
- Tone
- Post type
- Target audience
- Additional context or notes

The frontend sends this information to the Java Spring Boot backend. The backend calls an external Python AI API, receives the generated content, stores the result, and returns the generated LinkedIn post draft to the frontend.

The application is limited to generating LinkedIn post drafts. It does not publish posts to LinkedIn, schedule posts, analyze engagement, or use the LinkedIn API.

## Technology Stack

### Frontend

- React
- Vite
- JavaScript
- CSS

### Backend

- Java
- Spring Boot
- Maven
- H2 database

### AI Integration

- Python FastAPI AI service
- OpenAI integration through the Python AI API
- Java backend communicates with the deployed Python AI API

### Deployment

- Frontend: Vercel
- Backend: Render
- Python AI API: Render

## Repository Structure

```text
linkedin-api/
  backend/       Java Spring Boot backend
  frontend/      React/Vite frontend
  python-api/    Python FastAPI AI API source code for thesis reference
```

## Backend

The backend is responsible for:

- Receiving post generation requests
- Validating user input
- Calling the Python AI API
- Returning generated LinkedIn content
- Storing generated posts in the database
- Providing API endpoints for the frontend

Local backend URL:

```text
http://localhost:15150
```

Health check:

```text
http://localhost:15150/api/health
```

Run backend locally:

```bash
cd backend
./mvnw test
./mvnw spring-boot:run
```

For Windows PowerShell:

```powershell
cd backend
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

## Frontend

The frontend is responsible for:

- Displaying the user interface
- Collecting post generation inputs
- Sending requests to the backend
- Displaying generated LinkedIn post drafts
- Providing a simple MVP demo login screen

Local frontend URL:

```text
http://localhost:5173
```

Run frontend locally:

```bash
cd frontend
npm install
npm run dev
```

Create production build:

```bash
npm run build
```

## Environment Variables

### Backend Environment Variables

```text
AI_PYTHON_BASE_URL=https://linkedin-generator-api.onrender.com
AI_PYTHON_GENERATE_PATH=/api/ai/generate-linkedin-post
FRONTEND_ALLOWED_ORIGINS=https://linkedin-api-ochre.vercel.app,http://localhost:5173,http://127.0.0.1:5173
SPRING_H2_CONSOLE_ENABLED=false
```

The backend uses the following port configuration:

```properties
server.port=${PORT:15150}
```

This means:

- Locally, the backend runs on port `15150`
- On Render, the backend uses the platform-provided `PORT`

To enable the H2 console locally, set:

```text
SPRING_H2_CONSOLE_ENABLED=true
```

### Frontend Environment Variables

For local development, create a `.env` file based on `.env.example`:

```text
VITE_API_BASE_URL=http://localhost:15150
```

For Vercel deployment:

```text
VITE_API_BASE_URL=https://linkedin-thesis-backend.onrender.com
```

## Deployment

### Backend Deployment on Render

The backend is deployed on Render using Docker.

Render settings:

```text
Root Directory: backend
Environment: Docker
Dockerfile Path: Dockerfile
```

Required backend environment variables:

```text
AI_PYTHON_BASE_URL=https://linkedin-generator-api.onrender.com
AI_PYTHON_GENERATE_PATH=/api/ai/generate-linkedin-post
FRONTEND_ALLOWED_ORIGINS=https://linkedin-api-ochre.vercel.app,http://localhost:5173,http://127.0.0.1:5173
SPRING_H2_CONSOLE_ENABLED=false
```

Backend health check after deployment:

```text
https://linkedin-thesis-backend.onrender.com/api/health
```

### Frontend Deployment on Vercel

The frontend is deployed on Vercel.

Vercel settings:

```text
Root Directory: frontend
Framework Preset: Vite
Build Command: npm run build
Output Directory: dist
```

Required frontend environment variable:

```text
VITE_API_BASE_URL=https://linkedin-thesis-backend.onrender.com
```

Live frontend:

```text
https://linkedin-api-ochre.vercel.app
```

## Python AI API

The `/python-api` folder contains the Python FastAPI AI service source code.

This folder is included for:

- Thesis documentation
- Source-code reference
- Showing the complete system architecture

The Python AI API is already deployed separately on Render and is not deployed from this repository.

Python AI API base URL:

```text
https://linkedin-generator-api.onrender.com
```

The Java backend calls the deployed Python AI API during normal application usage.

## MVP Scope

This project is an MVP for thesis purposes.

Included features:

- LinkedIn post topic input
- Tone selection
- Post type selection
- Target audience input
- Optional context input
- AI-generated LinkedIn post draft
- Generated content preview
- Content saving in backend database
- Simple frontend demo login screen

Not included:

- Real LinkedIn login
- LinkedIn API integration
- Automatic publishing
- Post scheduling
- Engagement analytics
- User authentication system
- Production-level user account management

## Dummy Login

The login screen is frontend-only and is used for MVP demonstration purposes.

It does not provide real authentication or user account management.

## System Flow

```text
User
  ↓
React/Vite Frontend
  ↓
Java Spring Boot Backend
  ↓
Python FastAPI AI Service
  ↓
OpenAI
  ↓
Generated LinkedIn Post
```

## Thesis Purpose

This repository supports the practical implementation part of the Bachelor thesis. It demonstrates how an AI-powered web application can be designed, developed, deployed, and integrated with an external AI service for LinkedIn content generation.

## Author

Syed Suhel
