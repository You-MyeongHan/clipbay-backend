package com.homepage.login.dto;

import lombok.Data;

@Data
public class MemberDto {
	private String userNo;
	private String userId;
	private String userPass;
	private String firstName;
	private String lastName;
	private String roleName;
	private String enabled;
	private String registDate;
}
