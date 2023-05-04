package com.homepage.user.service;

import com.homepage.user.dto.JwtTokenDto;
import com.homepage.user.entity.User;

public interface UserService {
	public JwtTokenDto login(String userId, String userPass);
}
