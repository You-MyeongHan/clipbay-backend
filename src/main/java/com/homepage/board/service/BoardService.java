package com.homepage.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homepage.board.entity.Board;
import com.homepage.board.entity.BoardRequest;
import com.homepage.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	public Board getBoardById(Long boardId) {
		var a=boardRepository.findWithUserNickById(boardId);
		log.debug(""+a);
		return a;
	}
	
	public Page<Board> findAll(Pageable pageable, String category) {
		Specification<Board> spec = null;
		if(category !=null && !category.isEmpty()) {
			spec = (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("category"), category);
		}
		
		if (spec != null) {
            return boardRepository.findAll(spec, pageable);
        } else {
            return boardRepository.findAll(pageable);
        }
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