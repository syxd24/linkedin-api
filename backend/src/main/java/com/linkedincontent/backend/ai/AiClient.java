package com.linkedincontent.backend.ai;

import com.linkedincontent.backend.post.dto.GeneratePostRequest;

public interface AiClient {

	String generatePost(GeneratePostRequest request);
}
