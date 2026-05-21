package com.linkedincontent.backend.post;

public class PostNotFoundException extends RuntimeException {

	public PostNotFoundException(Long id) {
		super("Generated post with id " + id + " was not found");
	}
}
