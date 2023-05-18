package com.homepage.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homepage.board.entity.BoardRequest;
import com.homepage.board.entity.PostResponse;
import com.homepage.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	
	@PostMapping("/register")
	public ResponseEntity<Boolean> register(
			@RequestBody BoardRequest request
	){
		return ResponseEntity.ok(boardService.register(request));
	}
	
	@GetMapping("/post")
	public ResponseEntity<Page<PostResponse>> find(@PageableDefault(page=0, size = 20, sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable){
		Page<PostResponse> boards = boardService.findAll(pageable).map(PostResponse::from);
        return ResponseEntity.ok(boards);
	}
}