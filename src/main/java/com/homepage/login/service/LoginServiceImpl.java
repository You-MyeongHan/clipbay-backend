package com.homepage.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.homepage.login.dto.MemberDto;
import com.homepage.login.mapper.LoginMapper;

public class LoginServiceImpl implements LoginService{
	@Autowired
	private LoginMapper loginMapper;
	
	public String checkUser(MemberDto mbr) throws Exception{
		List<MemberDto> memberList = loginMapper.selectMembers(mbr.getUserId());
		if(memberList.isEmpty()) {
			return "계정이 존재하지 않습니다.";
		}
		
		MemberDto member=memberList.get(0);
		if(!member.getUserPass().equals(mbr.getUserPass())) {
			return "비밀번호가 일치하지 않습니다.";
		}
		else if(!member.getEnabled().equals("Y")) {
			return "비활성화 된 계정입니다."; 
		}
		
		return "OK";
	}
}
