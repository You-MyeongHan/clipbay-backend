package com.homepage.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.homepage.board.entity.Board;
import com.homepage.board.entity.BoardRequest;
import com.homepage.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	public Page<Board> findAll(Pageable pageable) {
		
		return boardRepository.findAll(pageable);
	}
	
	public boolean register(BoardRequest request) {
		
		var board=Board.builder()
				.title(request.getTitle())
				.category(request.getCategory())
				.content(request.getContent())
				.user(request.getUser_id())
				.build();
		boardRepository.save(board);
		
		return true;
	}
}