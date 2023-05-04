package com.homepage.user.controller;

import java.util.Map;
import com.homepage.user.dto.JwtTokenDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homepage.user.service.UserService;

@RestController
//@RequestMapping("/")
public class UserController {
	
	private final UserService service;
	
	public UserController(UserService service) {
		this.service=service;
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtTokenDto> loginSuccess(@RequestBody Map<String, String> loginForm){
		JwtTokenDto token= service.login(loginForm.get("userId"), loginForm.get("userPass"));
		return ResponseEntity.ok(token);
	}
	 
}
