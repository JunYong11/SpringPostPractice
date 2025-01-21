package com.example.post.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.post.model.User;

@Repository
public class UserRepository {
	private static Long sequence = 0L;	// ID 자동 증가를 위한 시퀀스
	private final Map<Long,User> store = new HashMap<>();

	// User 저장
	public User save(User user) {
		user.setId(++sequence);
		store.put(user.getId(), user);
		return user;
	}
	
	public Optional<User> findById(Long id){
		return Optional.ofNullable(store.get(id));
	}
	
	
	public List<User> findAll(){
		return new ArrayList<>(store.values());
	}

	public User update(User user) {
		// TODO Auto-generated method stub
		store.put(user.getId(), user);
		return null;
	}
}
