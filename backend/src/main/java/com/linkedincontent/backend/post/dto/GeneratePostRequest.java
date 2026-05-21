package com.linkedincontent.backend.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GeneratePostRequest {

	@NotBlank(message = "Topic is required")
	@Size(min = 3, max = 200, message = "Topic must be between 3 and 200 characters")
	private String topic;

	@NotBlank(message = "Tone is required")
	private String tone;

	@NotBlank(message = "Post type is required")
	private String postType;

	@NotBlank(message = "Target audience is required")
	@Size(min = 3, max = 150, message = "Target audience must be between 3 and 150 characters")
	private String targetAudience;

	@Size(max = 1000, message = "Optional context must be at most 1000 characters")
	private String optionalContext;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTone() {
		return tone;
	}

	public void setTone(String tone) {
		this.tone = tone;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getTargetAudience() {
		return targetAudience;
	}

	public void setTargetAudience(String targetAudience) {
		this.targetAudience = targetAudience;
	}

	public String getOptionalContext() {
		return optionalContext;
	}

	public void setOptionalContext(String optionalContext) {
		this.optionalContext = optionalContext;
	}
}
