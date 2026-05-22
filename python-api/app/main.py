from fastapi import FastAPI, HTTPException, Request
from fastapi.responses import JSONResponse
from pydantic import ValidationError

from app.schemas import GeneratePostRequest, GeneratePostResponse
from app.prompt_builder import build_linkedin_post_prompt
from app.openai_client import generate_linkedin_post
from app.errors import OpenAIServiceError

app = FastAPI(
    title="LinkedIn Content AI Service",
    description="AI service for LinkedIn post draft generation",
    version="1.0.0",
)


@app.get("/api/health")
def health_check():
    return {
        "status": "UP",
        "service": "linkedin-content-ai-service"
    }


@app.post(
    "/api/ai/generate-linkedin-post",
    response_model=GeneratePostResponse
)
def generate_post(request: GeneratePostRequest):
    try:
        prompt = build_linkedin_post_prompt(request)
        generated_content = generate_linkedin_post(prompt)

        return GeneratePostResponse(
            generatedContent=generated_content
        )

    except OpenAIServiceError as exc:
        raise HTTPException(
            status_code=502,
            detail={
                "error": "AI service failed",
                "message": str(exc),
                "status": 502
            }
        )


@app.exception_handler(HTTPException)
async def http_exception_handler(request: Request, exc: HTTPException):
    if isinstance(exc.detail, dict):
        return JSONResponse(
            status_code=exc.status_code,
            content=exc.detail
        )

    return JSONResponse(
        status_code=exc.status_code,
        content={
            "error": "Request failed",
            "message": str(exc.detail),
            "status": exc.status_code
        }
    )