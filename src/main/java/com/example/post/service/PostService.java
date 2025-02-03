package com.example.post.service;

import java.util.List;

import com.example.post.model.posts.Post;

public interface PostService {

	// 글 저장
	public Post savePost(Post post);
	
	// 글 전체 조회
	public List<Post> getAllPosts();
	
	// 아이디로 글 조회
	public Post getPostById(Long postId);
	
	// 글 삭제
	public void removePost(Long postId);
	
	// 글 수정
	public void updatePost(Long postId, Post updatePost);
	
	// 글 읽기
	public Post readPost(Long postId);
}
