package com.linkedincontent.backend.post;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkedincontent.backend.post.dto.GeneratePostRequest;
import com.linkedincontent.backend.post.dto.GeneratedPostResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("/generate")
	public GeneratedPostResponse generatePost(@Valid @RequestBody GeneratePostRequest request) {
		return postService.generatePost(request);
	}

	@GetMapping("/history")
	public List<GeneratedPostResponse> getHistory() {
		return postService.getHistory();
	}

	@GetMapping("/{id}")
	public GeneratedPostResponse getPost(@PathVariable Long id) {
		return postService.getPost(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}
}
