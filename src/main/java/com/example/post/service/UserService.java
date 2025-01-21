package com.example.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.post.model.User;
import com.example.post.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	/*
	 * 의존성 주입 방법
	 * 1. 필드 주입
	 * 2. 생성자 주입
	 * 3. 세터 주입
	 */
	@Autowired	// 필드 주입 (new 쓰는거랑 같은 효과)
	private UserRepository userRepository;
	
	//@Autowired // 생성자 주입
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// 사용자 등록
	public User registerUser(User user) {
		return userRepository.save(user);
	}
	
	// 세터 주입
	public void setUserRepositort(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	// 사용자 조회
	public User searchUser(Long id) {
		Optional<User> result = userRepository.findById(id);
		
		if(result.isPresent()) {
			return result.get();
		}
		throw new RuntimeException("회원정보가 없습니다.");
		// TODO Auto-generated method stub
		
	}

	public List<User> findAll() {
		// TODO Auto-generated method stub
		List<User> list = new ArrayList<>();
		list = userRepository.findAll();
		return list;
	}

	// 사용자 수정
	public User registerUserUpdate(User user) {
		// TODO Auto-generated method stub
		return userRepository.update(user);
	}
	
}
