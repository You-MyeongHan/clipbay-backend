package com.homepage.security.auth;

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
	private String password;
	private String nickname;
	private String email;
	private Boolean emailReceive;
}
