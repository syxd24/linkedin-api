from pydantic import BaseModel, Field


class GeneratePostRequest(BaseModel):
    topic: str = Field(..., min_length=3, max_length=200)
    tone: str = Field(..., min_length=2, max_length=50)
    postType: str = Field(..., min_length=2, max_length=80)
    targetAudience: str = Field(..., min_length=3, max_length=150)
    optionalContext: str | None = Field(default=None, max_length=1000)


class GeneratePostResponse(BaseModel):
    generatedContent: str


class ErrorResponse(BaseModel):
    error: str
    message: str
    status: int