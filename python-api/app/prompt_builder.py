from app.schemas import GeneratePostRequest


def build_linkedin_post_prompt(request: GeneratePostRequest) -> str:
    optional_context = request.optionalContext.strip() if request.optionalContext else "Not provided."

    return f"""
You are an assistant that writes professional LinkedIn post drafts.

Write a LinkedIn post based on the following details:
Topic: {request.topic}
Tone: {request.tone}
Post type: {request.postType}
Target audience: {request.targetAudience}
Additional context: {optional_context}

Requirements:
- Write only the LinkedIn post draft.
- Keep the post suitable for professional communication.
- Avoid unsupported claims.
- Make the post clear and readable.
- Do not include explanations outside the post.
- Do not mention that you are an AI.
- Do not publish or schedule anything.
- The user will review and edit the draft manually before posting.
""".strip()