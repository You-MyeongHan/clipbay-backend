package com.homepage.security.auth.entity;

import com.homepage.security.user.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String uid;
	private String pwd;
	private String nick;
	private String email;
	private Boolean emailReceive;
	private Role role;
}
