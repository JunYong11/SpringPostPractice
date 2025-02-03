package com.example.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.post.model.users.User;
import com.example.post.model.users.UserCreateDto;
import com.example.post.model.users.UserLoginDto;
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
	 * 
	 * Spring Data JPA 의 CRUD
	 * Create : save(엔티티 객체)
	 * Read : findBtId(엔티티 객체의 아이디), findAll()
	 * Update : 없음(영속성 컨텍스트에서 변경 감지를 통해 업데이트)
	 * Delete : delete(엔티티 객체)
	 * 
	 */
	@Autowired	// 필드 주입 (new 쓰는거랑 같은 효과)
	private UserRepository userRepository;
	
	//@Autowired // 생성자 주입
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// 사용자 등록
	public User registerUser(UserCreateDto userCreatDto) {
		User user = new User();
		user.setUsername(userCreatDto.getUsername());
		user.setPassword(userCreatDto.getPassword());
		user.setName(userCreatDto.getName());
		user.setGender(userCreatDto.getGender());
		user.setEmail(userCreatDto.getEmail());
		user.setBirthDate(userCreatDto.getBirthDate());
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
		
	}

	public List<User> findAll() {
		// TODO Auto-generated method stub
		List<User> list = new ArrayList<>();
		list = userRepository.findAll();
		return list;
	}

	// 아이디 체크
	public User getUserbyUsername(String username) {
		// TODO Auto-generated method stub
		
		return userRepository.findByUsername(username);
	}
}
