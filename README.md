# Development of an AI-Powered Web Application for LinkedIn Content Generation

This repository contains the MVP implementation for my VMU Bachelor thesis project: an AI-powered web application that helps users generate LinkedIn post drafts.

## Live Demo

Frontend:  
https://linkedin-api-ochre.vercel.app

Backend health check:  
https://linkedin-thesis-backend.onrender.com/api/health

GitHub repository:  
https://github.com/syxd24/linkedin-api

## Project Overview

The system allows a user to enter LinkedIn post details, such as topic, tone, post type, target audience, and optional context. The Java Spring Boot backend sends the request to a Python AI API, receives generated content, and returns the final LinkedIn post draft to the frontend.

The application is limited to LinkedIn post draft generation. It does not publish posts, schedule posts, analyze engagement, or use the LinkedIn API.

## Technology Stack

- Frontend: React, Vite, JavaScript, CSS
- Backend: Java, Spring Boot, Maven
- Database: H2 database configured in the Spring Boot backend
- AI Integration: Java backend calls an external Python AI API
- Deployment:
  - Frontend: Vercel
  - Backend: Render
  - Python AI API: Render

## Repository Structure

```text
linkedin-api/
  backend/      Java Spring Boot backend
  frontend/     React/Vite frontend
  python-api/   Python FastAPI AI service source code for thesis reference
