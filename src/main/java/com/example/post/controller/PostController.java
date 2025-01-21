package com.example.post.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.post.model.Post;
import com.example.post.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {
	private final PostService postService;
	
	// 게시글 작성 페이지 이동
	@GetMapping("posts/create")
	public String createPostForm() {
		// 게시글 작성 페이지의 뷰 이름을 리턴
		return "posts/create";
	}
	
	// 게시글 등록
	@PostMapping("posts")
	public String savePost(
			@ModelAttribute Post post) {
		log.info("post: {}",post);
		Post savedPost = postService.savePost(post);
		log.info("savedPost: {}",savedPost);
		return "redirect:/posts";
	}
	
	// 게시글 목록 조회
	@GetMapping("posts")
	public String listPosts(Model model) {
		List<Post> posts = postService.getAllPosts();
		model.addAttribute("posts",posts);
		return "posts/list";
	}
	
	// 게시글 조회
	@GetMapping("posts/{id}")
	public String contentPost(
			@PathVariable(name = "id") long id, 
			Model model) {
		Post findPost = postService.getPostById(id);
		model.addAttribute("post",findPost);
		return "posts/view";
	} 
	
	// 게시글 삭제
	@PostMapping("posts/remove/{id}")
	public String removePost(
			@PathVariable(name = "id") Long id,
			@RequestParam(name = "password")String password) {
		postService.removePost(id, password);
		return "redirect:/posts";
	}
}
