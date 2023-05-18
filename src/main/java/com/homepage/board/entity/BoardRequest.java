package com.homepage.board.entity;

import com.homepage.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {
	private User user_id;
	private String title;
	private String category;
	private String content;
}