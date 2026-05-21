package com.linkedincontent.backend.post.dto;

import java.time.LocalDateTime;

public class GeneratedPostResponse {

	private Long id;
	private String topic;
	private String tone;
	private String postType;
	private String targetAudience;
	private String optionalContext;
	private String generatedContent;
	private LocalDateTime createdAt;

	public GeneratedPostResponse(Long id, String topic, String tone, String postType, String targetAudience,
			String optionalContext, String generatedContent, LocalDateTime createdAt) {
		this.id = id;
		this.topic = topic;
		this.tone = tone;
		this.postType = postType;
		this.targetAudience = targetAudience;
		this.optionalContext = optionalContext;
		this.generatedContent = generatedContent;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public String getTopic() {
		return topic;
	}

	public String getTone() {
		return tone;
	}

	public String getPostType() {
		return postType;
	}

	public String getTargetAudience() {
		return targetAudience;
	}

	public String getOptionalContext() {
		return optionalContext;
	}

	public String getGeneratedContent() {
		return generatedContent;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
