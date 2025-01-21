package com.example.post.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.post.model.User;
import com.example.post.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor	// 롬복 생성자 주입 어노테이션
@Controller
public class UserController {
	
	private final UserService userSerivce;

	//회원가입 페이지 요청 처리
	@GetMapping(path="register")
	public String register() {
		return "register";
	}
	
	//회원가입 요청 처리
	/*
	@PostMapping(path="register_v3")
	public String registerUser(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email) {
		log.info("name:{}",name);
		log.info("email:{}",email);
		
		return "register_success";
	}
	*/
	@PostMapping(path="register")
	public String registerUser(
			@ModelAttribute User user) {
		User registedUser = userSerivce.registerUser(user);
		return "register_success";
	}
	
	// ID로 회원 정보 조회하기 ex) /user-details/{사용자ID} 
	// -> 정보를 조회하여 
	// -> user_detail.html을 보여준다,
	
	@GetMapping(path = "user-details/{id}")
	public String userDetails(@PathVariable("id") Long id, Model model) {
		User user = userSerivce.searchUser(id);
		model.addAttribute("User",user);
		return "user_detail";
	}

	@GetMapping(path="user-list")
	public String userList(Model model){
		List<User> list = new ArrayList<>();
		
		list = userSerivce.findAll();
		model.addAttribute("list",list);
		return "user_list";
	}
	
	@PostMapping(path = "user-update")
	public String userUpdate(
			@ModelAttribute User user, Model model) {
		model.addAttribute("user",user);
		log.info("user: {}",user);
		return "user_update";
	}
	
	
	@PostMapping(path="register_v4")
	public String registerUserUpdate(
			@ModelAttribute User user) {
		User registedUser = userSerivce.registerUserUpdate(user);
		return "register_success_update";
	}
}
