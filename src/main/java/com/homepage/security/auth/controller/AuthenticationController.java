package com.homepage.security.auth.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	private final AuthenticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<Boolean> register(
			@RequestBody RegisterRequest request
	){
		return ResponseEntity.ok(service.register(request));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request
	){
		return ResponseEntity.ok(service.authenticate(request));
	}
	
	@PostMapping("/refresh-token")
	  public void refreshToken(
	      HttpServletRequest request,
	      HttpServletResponse response
	  ) throws IOException {
	    service.refreshToken(request, response);
	  }
}