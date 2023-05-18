package com.homepage.board.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("content")
	private String content;
	@JsonProperty("wr_date")
	private String wr_date;
	@JsonProperty("view_cnt")
	private int view_cnt;
}