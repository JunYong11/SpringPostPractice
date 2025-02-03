package com.example.post.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
import com.example.post.service.PostServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {
	private final PostServiceImpl postService;
	
	// 게시글 작성 페이지 이동
	@GetMapping("posts/create")
	public String createPostForm(
			// @SessionAttribute 세션에 있는 데이터를 꺼내는 어트리뷰트 (세션에 저장된 데이터를 조회한다.)
			@SessionAttribute(name = "loginUser", required = false) User loginUser) {
		// 사용자가 로그인을 했는지 체크
		log.info("loginUser: {}", loginUser);
		if(loginUser == null) {
			// 로그인을 하지 않았으면 로그인 페이지로 리다이렉트
			return "redirect:/users/login";
		}
		// 게시글 작성 페이지의 뷰 이름을 리턴
		return "posts/create";
	}
	
	// 게시글 등록
	@PostMapping("posts")
	public String savePost(
			@ModelAttribute Post post,
			@SessionAttribute(name = "loginUser") User loginUser) {
		log.info("post: {}",post);
		post.setUser(loginUser);
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
		Post findPost = postService.readPost(id);
		model.addAttribute("post",findPost);
		return "posts/view";
	} 
	
	// 게시글 삭제
	@GetMapping("posts/remove/{id}")
	public String removePost(
			@PathVariable(name = "id") Long id,
			@SessionAttribute(name = "loginUser") User loginUser) {
		// 삭제하려고 하는 게시글이 로그인 사용자가 작성한 글인지 확인
		Post findPost = postService.getPostById(id);
		if(findPost == null || findPost.getUser().getId() != loginUser.getId()) {
			return "redurect:/posts";
		}
		postService.removePost(id);
		return "redirect:/posts";
	}
	
	// 게시글 수정 폼
	@GetMapping("posts/edit/{postId}")
	public String updatePostForm(
			@PathVariable(name="postId") Long id,
			@SessionAttribute(name = "loginUser") User loginUser,
			Model model) {
		model.addAttribute("post",postService.getPostById(id));
		return "posts/update";
	}
	
	// 게시글 수정
	@PostMapping("posts/edit/{postId}")
	public String updatePost(
			@PathVariable(name="postId") Long id,
			@ModelAttribute Post updatePost,
			@SessionAttribute(name = "loginUser") User loginUser) {
		
		// 수정 하려는 게시글이 존재하고 로그인 사용자와 게시글의 작성자가 같은지 확인
		Post post = postService.getPostById(id);
		if(post.getUser().getId() == loginUser.getId()) {
			updatePost.setId(id);
			postService.updatePost(id, updatePost);
		}
		
		return "redirect:/posts";
	}

}
