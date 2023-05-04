package com.homepage.user.dto;

import lombok.Data;

@Data
public class UserDto {
	private String userNo;
	private String userId;
	private String userPass;
	private String firstName;
	private String lastName;
	private String roleName;
	private String enabled;
	private String registDate;
}
