package com.linkedincontent.backend.post;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkedincontent.backend.ai.AiClient;
import com.linkedincontent.backend.post.dto.GeneratePostRequest;
import com.linkedincontent.backend.post.dto.GeneratedPostResponse;

@Service
public class PostService {

	private final GeneratedPostRepository generatedPostRepository;
	private final AiClient aiClient;

	public PostService(GeneratedPostRepository generatedPostRepository, AiClient aiClient) {
		this.generatedPostRepository = generatedPostRepository;
		this.aiClient = aiClient;
	}

	@Transactional
	public GeneratedPostResponse generatePost(GeneratePostRequest request) {
		String generatedContent = aiClient.generatePost(request);

		GeneratedPost generatedPost = new GeneratedPost();
		generatedPost.setTopic(request.getTopic());
		generatedPost.setTone(request.getTone());
		generatedPost.setPostType(request.getPostType());
		generatedPost.setTargetAudience(request.getTargetAudience());
		generatedPost.setOptionalContext(request.getOptionalContext());
		generatedPost.setGeneratedContent(generatedContent);

		return toResponse(generatedPostRepository.save(generatedPost));
	}

	@Transactional(readOnly = true)
	public List<GeneratedPostResponse> getHistory() {
		return generatedPostRepository.findAllByOrderByCreatedAtDesc()
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public GeneratedPostResponse getPost(Long id) {
		return generatedPostRepository.findById(id)
				.map(this::toResponse)
				.orElseThrow(() -> new PostNotFoundException(id));
	}

	@Transactional
	public void deletePost(Long id) {
		if (!generatedPostRepository.existsById(id)) {
			throw new PostNotFoundException(id);
		}
		generatedPostRepository.deleteById(id);
	}

	private GeneratedPostResponse toResponse(GeneratedPost generatedPost) {
		return new GeneratedPostResponse(
				generatedPost.getId(),
				generatedPost.getTopic(),
				generatedPost.getTone(),
				generatedPost.getPostType(),
				generatedPost.getTargetAudience(),
				generatedPost.getOptionalContext(),
				generatedPost.getGeneratedContent(),
				generatedPost.getCreatedAt());
	}
}
