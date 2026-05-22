from fastapi.testclient import TestClient

from app.main import app

client = TestClient(app)


def test_generate_post_validation_error():
    response = client.post(
        "/api/ai/generate-linkedin-post",
        json={
            "topic": "",
            "tone": "Professional",
            "postType": "Educational",
            "targetAudience": "Junior developers"
        }
    )

    assert response.status_code == 422


def test_generate_post_success_mock(monkeypatch):
    def fake_generate_linkedin_post(prompt: str) -> str:
        return "This is a generated LinkedIn post draft."

    monkeypatch.setattr(
        "app.main.generate_linkedin_post",
        fake_generate_linkedin_post
    )

    response = client.post(
        "/api/ai/generate-linkedin-post",
        json={
            "topic": "How junior developers can improve problem-solving skills",
            "tone": "Professional",
            "postType": "Educational",
            "targetAudience": "Junior software developers",
            "optionalContext": "Mention practice, debugging, and learning from mistakes"
        }
    )

    assert response.status_code == 200
    assert response.json() == {
        "generatedContent": "This is a generated LinkedIn post draft."
    }