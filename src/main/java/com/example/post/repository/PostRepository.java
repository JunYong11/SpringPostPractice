package com.example.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.post.model.posts.Post;

public interface PostRepository extends JpaRepository<Post, Long> {	// 인터페이스는 접근제어자가 public 이라서 구지 안붙여도 됨
	
	
}
