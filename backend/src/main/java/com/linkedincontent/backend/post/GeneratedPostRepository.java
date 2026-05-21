package com.linkedincontent.backend.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneratedPostRepository extends JpaRepository<GeneratedPost, Long> {

	List<GeneratedPost> findAllByOrderByCreatedAtDesc();
}
