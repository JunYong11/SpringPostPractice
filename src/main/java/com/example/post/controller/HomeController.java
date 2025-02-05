package com.example.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		log.info("home controller");
		
		return "index";
	}
}
