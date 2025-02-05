package com.example.post.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.post.model.users.User;
import com.example.post.model.users.UserCreateDto;
import com.example.post.model.users.UserLoginDto;
import com.example.post.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor	// 롬복 생성자 주입 어노테이션
@Controller
public class UserController {
	
	private final UserService userSerivce;
	//회원가입 페이지 요청 처리
	@GetMapping(path="users/register")
	public String register(Model model) {
		model.addAttribute("RegisterUserDto", new UserCreateDto());
		return "users/register";
	}
	
	@PostMapping(path="users/register")
	public String registerUser(
			@Validated  @ModelAttribute UserCreateDto userCreatDto,
			BindingResult bindingResult // Validated 검증 결과를 받아주는 변수
			) {
		
		// 유효성 검증이 실패 했는지 확인
		if(bindingResult.hasErrors()) {
			log.info("유효성 검증 실패");
			return "/users/register";
		}
		/*
		User registedUser = userSerivce.registerUser(user);
		log.info("=====================회원 가입 완료=====================");
		log.info("아이디: {}",registedUser.getUsername());
		log.info("비밀번호: {}",registedUser.getPassword());
		log.info("이름: {}",registedUser.getName());
		log.info("성별: {}",registedUser.getGender());
		log.info("생년월일: {}",registedUser.getBirthDate());
		log.info("이메일: {}",registedUser.getEmail());
		*/
		
		// username 중복 확인
		if(userSerivce.getUserbyUsername(userCreatDto.getUsername()) != null) {
			// 이미 사용중인 username이 있다.
		 bindingResult.reject("duplicate.username","이미 사용중인 아이디입니다.");
		 return "/users/register";
		}
		User registedUser = userSerivce.registerUser(userCreatDto.toEntity());
		return "redirect:/";
	}
	
	// 로그인 페이지 이동
	@GetMapping("users/login")
	public String loginForm(
			Model model) {
		model.addAttribute("userLoginDto",new UserLoginDto());
		return "users/login";
	}
	
	// 로그인
	@PostMapping("users/login")
	public String login(
			@Validated @ModelAttribute UserLoginDto userLoginDto,
			BindingResult bindingResult,
			HttpServletRequest request,
			@RequestParam(name = "redirectURL") String redirectURL) {
		// 로그인 검증 실패시
		if(bindingResult.hasErrors()) {
			log.info("유효성 검증 실패");
			return "users/login";
		}
		// username 해당하는 User 객체를 찾는다.
		User findUser = userSerivce.getUserbyUsername(userLoginDto.getUsername());
		if(findUser == null || findUser.getPassword().equals(userLoginDto.getPassword()) != true) {
			log.info("===================== 로그인 실패 =====================");
			log.info("findUser: {}",findUser);
			log.info("입력한 아이디: {}",userLoginDto.getUsername());
			log.info("입력한 패스워드: {}",userLoginDto.getPassword());
			
			bindingResult.reject("loginFailed", "아이디 또는 패스워드가 일치하지 않습니다.");
			return "/users/login";
		}
	
		// Request 객체에 저장돼 있는 Session 객체를 받아온다.
		HttpSession session = request.getSession();
		// session 에 로그인 정보를 저장한다.
		session.setAttribute("loginUser", findUser);
		log.info("===================== 로그인 성공 =====================");
		User loginUser = (User) session.getAttribute("loginUser");
		log.info("세션 정보: {}", loginUser);
		
		return "redirect:" + redirectURL;
	}
	
	// 세션 저장 확인
	@ResponseBody
	@GetMapping("loginCheck")
	public String loginCheck(
			HttpServletRequest request) {
		// Request 객체에 저장돼 있는 Session 객체를 받아온다.
		HttpSession session = request.getSession();
		// session 에 저장된 loginUsername 정보를 찾는다.
		// getAttribute 는 리턴 타입이 Object 이기 때문에 형변환을 해줘야 한다.
		String loginUser = (String) session.getAttribute("loginUser");
		log.info("loginUser: {}",loginUser);
		
		return "ok";
	}
	
	//	로그아웃
	@GetMapping("users/logout")
	public String logout(
			HttpSession session) {
		//session.setAttribute("loginUser", null);
		// session 의 데이터를 모두 삭제한다.
		session.invalidate();
		log.info("===================== 로그아웃 성공 =====================");
		log.info("세션 정보: {}",session);
		return "redirect:/";
	}
	
}