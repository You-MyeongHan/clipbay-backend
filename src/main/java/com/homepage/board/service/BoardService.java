package com.homepage.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homepage.board.entity.Board;
import com.homepage.board.entity.BoardRequest;
import com.homepage.board.repository.BoardRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final EntityManagerFactory emf;
	
	public Board getBoardById(Long boardId) {
		return boardRepository.findWithUserNicknameById(boardId);
	}
	
	public Page<Board> findAll(Pageable pageable) {
		
		return boardRepository.findAll(pageable);
	}
	
	@Transactional
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