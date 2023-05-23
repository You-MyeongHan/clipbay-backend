package com.homepage.security.auth.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homepage.security.auth.entity.AuthenticationRequest;
import com.homepage.security.auth.entity.AuthenticationResponse;
import com.homepage.security.auth.entity.RegisterRequest;
import com.homepage.security.auth.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Boolean> register(
			@RequestBody RegisterRequest request
	){
		return ResponseEntity.ok(userService.register(request));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request
	){
		return ResponseEntity.ok(userService.authenticate(request));
	}
	
	@PostMapping("/logout")
	public void logout(
			HttpServletRequest request,
		    HttpServletResponse response
	){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		userService.logout(request, response, auth);
	}
	
	@GetMapping("/checkUid")
	public ResponseEntity<?> checkUidDuplication(
			@RequestParam(value="uid") String uid){
		return ResponseEntity.ok(userService.existsByUid(uid));
	}
	
	@GetMapping("/checkEmail")
	public ResponseEntity<?> checkEmailDuplication(
			@RequestParam(value="email") String email){
		return ResponseEntity.ok(userService.existsByEmail(email));
	}
	
	@PostMapping("/refresh-token")
	  public void refreshToken(
	      HttpServletRequest request,
	      HttpServletResponse response
	  ) throws IOException {
		userService.refreshToken(request, response);
	  }
}