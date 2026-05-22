from openai import OpenAI

from app.config import settings
from app.errors import OpenAIServiceError


def generate_linkedin_post(prompt: str) -> str:
    if not settings.OPENAI_API_KEY:
        raise OpenAIServiceError("OpenAI API key is not configured.")

    try:
        client = OpenAI(api_key=settings.OPENAI_API_KEY)

        response = client.chat.completions.create(
            model=settings.OPENAI_MODEL,
            messages=[
                {
                    "role": "system",
                    "content": "You generate professional LinkedIn post drafts only."
                },
                {
                    "role": "user",
                    "content": prompt
                }
            ],
            temperature=0.7,
            max_tokens=600,
        )

        content = response.choices[0].message.content

        if not content:
            raise OpenAIServiceError("OpenAI returned an empty response.")

        return content.strip()

    except Exception as exc:
        raise OpenAIServiceError("Failed to generate LinkedIn post.") from exc