package com.homepage.board.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class PostResponse {
	private final String title;
//	private final String nick;
	private final LocalDateTime wr_date;
	private final Integer view_cnt;
	private final int recommend;
	
	public static PostResponse from(Board board) {
		return new PostResponse(board);
	}
	
	private PostResponse(Board board) {
		this.title=board.getTitle();
//		this.nick=board.getNick();
		this.wr_date=board.getWr_date();
		this.view_cnt=board.getView_cnt();
		this.recommend=board.getRecommend();
	}
}