package com.example.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.post.model.posts.Comment;
import com.example.post.repository.CommentRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	
	// 댓글 등록
	@Transactional
	public void addComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	// 댓글 전체 조회
	public List<Comment> getCommentsByPostId(Long postId) {
		return commentRepository.findAllByPostId(postId);
	}
	
	// 댓글 조회
	public Comment getComment(Long commentId) {
		// 댓글 ID에 내용을 조회
		return null;
	}
	// 댓글 수정
	public void editComment(Comment updateComment) {
		
	}
	
	
	// 댓글 삭제
	public void deleteComment(Long commentId) {
		
	}
	
	
}
