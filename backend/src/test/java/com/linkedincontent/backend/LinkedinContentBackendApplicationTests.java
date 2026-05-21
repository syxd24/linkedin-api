package com.linkedincontent.backend;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.linkedincontent.backend.ai.AiClient;

@AutoConfigureMockMvc
@SpringBootTest
class LinkedinContentBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AiClient aiClient;

	@Test
	void healthReturnsUp() throws Exception {
		mockMvc.perform(get("/api/health"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("UP"))
				.andExpect(jsonPath("$.service").value("linkedin-content-backend"));
	}

	@Test
	void generatePostWithValidRequestReturnsGeneratedContent() throws Exception {
		when(aiClient.generatePost(any())).thenReturn("Mock AI generated LinkedIn post");

		String requestJson = """
				{
				  "topic": "How junior developers can improve problem-solving skills",
				  "tone": "Professional",
				  "postType": "Educational",
				  "targetAudience": "Junior software developers",
				  "optionalContext": "Mention practice, debugging, and learning from mistakes"
				}
				""";

		mockMvc.perform(post("/api/posts/generate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.generatedContent").value(containsString("Mock AI generated LinkedIn post")))
				.andExpect(jsonPath("$.topic").value("How junior developers can improve problem-solving skills"));
	}

	@Test
	void generatePostWithEmptyTopicReturnsBadRequest() throws Exception {
		String requestJson = """
				{
				  "topic": "",
				  "tone": "Professional",
				  "postType": "Educational",
				  "targetAudience": "Junior software developers",
				  "optionalContext": "Mention practice, debugging, and learning from mistakes"
				}
				""";

		mockMvc.perform(post("/api/posts/generate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.error").value("Validation failed"))
				.andExpect(jsonPath("$.path").value("/api/posts/generate"));
	}

	@Test
	void historyReturnsOk() throws Exception {
		mockMvc.perform(get("/api/posts/history"))
				.andExpect(status().isOk());
	}

	@Test
	void missingPostReturnsNotFound() throws Exception {
		mockMvc.perform(get("/api/posts/999999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.path").value("/api/posts/999999"));
	}
}
