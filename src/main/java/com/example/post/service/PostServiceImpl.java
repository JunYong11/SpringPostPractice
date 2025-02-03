package com.example.post.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
import com.example.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional(readOnly = true)	// 데이터를 읽기 전용으로 가져온다
@Slf4j
@RequiredArgsConstructor	// 의존성 주입(생성자 주입)
@Service
public class PostServiceImpl implements PostService {
	// PostService 객체 생성 시점에 스프링 컨테이너가 자동으로 의존성을 주입(Dependency Injection) 해준다.
	private final PostRepository postRepository;
	
	// 글 저장
	@Transactional
	@Override
	public Post savePost(Post post) {
		post.setCreateTime(LocalDateTime.now());
		postRepository.save(post);
		return post;
	}
	
	// 글 전체 조회
	@Override
	public List<Post> getAllPosts(){
		return postRepository.findAll();
	}

	// 게시글 조회(조회수 증가)
	@Transactional
	@Override
	public Post readPost(Long postId) {
		Optional<Post> findPost = postRepository.findById(postId);
		if(findPost.isPresent()) {
			// 조회수 증가
			findPost.get().incrementViews();
			return findPost.get();
		}
		return null;
	}
	// 아이디로 글 조회
	@Transactional		// 삽입 삭제 수정이 발생하는 메소드에는 이걸 붙여야 한다.
	@Override
	public Post getPostById(Long postId) {
		Optional<Post> findPost = postRepository.findById(postId);
		if(findPost.isPresent()) {
			return findPost.get();
		}
		return null;
	}

	// 글 삭제
	@Transactional
	@Override
	public void removePost(Long postId) {
		Optional<Post> findPost = postRepository.findById(postId);
		if(findPost.isPresent()) {
			Post post = findPost.get();
			postRepository.delete(post);
		}
	}
	
	// 글 수정
	@Transactional
	@Override
	public void updatePost(Long postId, Post updatePost) {
		Optional<Post> findPost = postRepository.findById(updatePost.getId());
		if(findPost.isPresent()) {
			Post post = findPost.get();
			post.setTitle(updatePost.getTitle());
			post.setContent(updatePost.getContent()); 
		}
	}
}
