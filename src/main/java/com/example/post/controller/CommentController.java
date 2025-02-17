package com.example.post.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.post.model.posts.Comment;
import com.example.post.model.posts.CommentResponseDTO;
import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
import com.example.post.service.CommentService;
import com.example.post.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("api")
@RequiredArgsConstructor	// 생성자 주입 (final 꼭 붙여줘야함)
@RestController
public class CommentController {

	private final PostService postservice;
	private final CommentService commentservice;
	/*
	 * HTTP 요청 방식
	 * GET: 조회
	 * POST: 저장, 등록
	 * PUT: 교체
	 * PATCH: 일부교체, 수정
	 * DELET: 삭제
	 */
	
	// 댓글 작성(POST)
	@PostMapping("posts/{postId}/comments")
	public ResponseEntity<CommentResponseDTO> addComment(
			@PathVariable(name = "postId") Long postId,
			@RequestBody Comment comment,
			@SessionAttribute(name = "loginUser") User loginUser) throws JsonMappingException, JsonProcessingException{
		
		Post post = postservice.getPostById(postId);
		// 누가 썼는지
		comment.setPost(post);
		comment.setUser(loginUser);
		commentservice.addComment(comment);
		
		CommentResponseDTO commentResponDto = comment.toResponseDto();
		return ResponseEntity.ok(commentResponDto);
	}
	// 댓글 조회(GET)
	@GetMapping("posts/{postId}/comments")
	public ResponseEntity<List<CommentResponseDTO>> getComment(
			@PathVariable(name = "postId") Long postId){
		List<Comment> comments = commentservice.getCommentsByPostId(postId);
		
		List<CommentResponseDTO> commentResponseDtos = new ArrayList<>();
		for(Comment comment : comments) {
			commentResponseDtos.add(comment.toResponseDto());
		}
		
		return ResponseEntity.ok(commentResponseDtos);
	}
	
	// 댓글 수정(PUT, PATCH)
	@PutMapping("posts/{postId}/comments/{commentId}")
	public ResponseEntity<List<Comment>> editComment(){
		return ResponseEntity.ok(null);
	}
	
	// 댓글 삭제(DELET)
	@DeleteMapping("posts/{postId}/comments/{commentId}")
	public void deletComment(){
		
	}
	
	
}
