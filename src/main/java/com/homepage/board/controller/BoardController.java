package com.homepage.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homepage.board.entity.Board;
import com.homepage.board.entity.BoardRequest;
import com.homepage.board.entity.PostResponse;
import com.homepage.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("/{boardId}")
	public ResponseEntity<Board> getBoardById(@PathVariable("boardId") Long boardId){
		Board board= boardService.getBoardById(boardId);
		Integer newVisit=board.getView_cnt()+1;
		
		Board board_=Board.builder()
				.view_cnt(newVisit)
				.build();
		boardService.updateView_cnt(boardId, board_);
		
        return ResponseEntity.ok(board);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Boolean> register(
			@RequestBody BoardRequest request
	){
		return ResponseEntity.ok(boardService.register(request));
	}
	
	@GetMapping("/post")
	public ResponseEntity<Page<PostResponse>> post(
			@PageableDefault(page=0, size = 20, sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam(value = "category", defaultValue  = "humor") String group){
		Page<PostResponse> boards = boardService.findAll(pageable, group).map(PostResponse::from);
		return ResponseEntity.ok(boards);
	}
}