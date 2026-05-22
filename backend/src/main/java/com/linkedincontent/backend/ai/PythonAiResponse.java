package com.linkedincontent.backend.ai;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PythonAiResponse {

	@JsonAlias("generated_content")
	private String generatedContent;

	public String getGeneratedContent() {
		return generatedContent;
	}

	public void setGeneratedContent(String generatedContent) {
		this.generatedContent = generatedContent;
	}
}
