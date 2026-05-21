package com.linkedincontent.backend.post;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;

@Entity
public class GeneratedPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String topic;
	private String tone;
	private String postType;
	private String targetAudience;
	private String optionalContext;

	@Lob
	private String generatedContent;

	private LocalDateTime createdAt;

	@PrePersist
	void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

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

	public String getGeneratedContent() {
		return generatedContent;
	}

	public void setGeneratedContent(String generatedContent) {
		this.generatedContent = generatedContent;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
