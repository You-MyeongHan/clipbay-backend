package com.homepage.login.service;

import com.homepage.login.dto.MemberDto;

public interface LoginService {
	String checkUser(MemberDto mbr) throws Exception;
}
