package com.linkedincontent.backend.ai;

import org.springframework.util.StringUtils;

import com.linkedincontent.backend.post.dto.GeneratePostRequest;

public class DummyAiClient implements AiClient {

	@Override
	public String generatePost(GeneratePostRequest request) {
		String contextLine = "";
		if (StringUtils.hasText(request.getOptionalContext())) {
			contextLine = "\n\nContext to include: " + request.getOptionalContext();
		}

		return """
				Draft LinkedIn post:

				%s

				For %s, improving in this area starts with small, consistent practice. A %s post in a %s tone should make the idea useful, clear, and easy to apply.

				Here are a few practical points to consider:
				- Break the problem into smaller parts before writing code.
				- Debug with a clear hypothesis instead of guessing.
				- Review mistakes and turn them into repeatable lessons.

				Progress usually comes from repeating the fundamentals with attention and patience.

				What habit has helped you improve most?
				%s
				""".formatted(
				request.getTopic(),
				request.getTargetAudience(),
				request.getPostType(),
				request.getTone(),
				contextLine).trim();
	}
}
