# Python AI API Service

This folder contains the FastAPI source code for the LinkedIn content generation AI service used by the thesis MVP. The service receives structured post-generation requests, builds a prompt, calls the OpenAI API, and returns a generated LinkedIn post draft.

The service is included in this repository for documentation, thesis review, and source-code reference purposes. It is already deployed separately on Render, and the Java Spring Boot backend calls the deployed Python API URL during normal MVP usage.

## Technology Stack

- Python
- FastAPI
- Uvicorn
- Pydantic
- OpenAI Python SDK
- Pytest

## Install Dependencies

```powershell
cd python-api
python -m venv .venv
.\.venv\Scripts\Activate.ps1
pip install -r requirements.txt
```

## Required Environment Variables

Create a local `.env` file from `.env.example` when running the service locally. Do not commit `.env`.

```env
OPENAI_API_KEY=replace-with-your-openai-api-key
OPENAI_MODEL=gpt-4o-mini
APP_ENV=local
```

## Run Locally

```powershell
cd python-api
uvicorn app.main:app --reload
```

Default local API docs are available at:

```text
http://127.0.0.1:8000/docs
```

Health endpoint:

```text
GET /api/health
```

Generation endpoint:

```text
POST /api/ai/generate-linkedin-post
```

## Tests

```powershell
cd python-api
pytest
```

## Deployment Note

This Python API is not deployed from this repository. It is already deployed separately on Render. The Java backend in `/backend` is configured to call the deployed Python AI API URL for content generation.

## Security Note

Only `.env.example` with placeholder values should be committed. Real OpenAI keys, Render secrets, API keys, tokens, and internal secret values must stay outside Git.
