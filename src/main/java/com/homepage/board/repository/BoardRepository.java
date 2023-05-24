package com.homepage.board.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.homepage.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>,JpaSpecificationExecutor<Board>{
	@EntityGraph(value = "Board.userWithNick", type = EntityGraph.EntityGraphType.FETCH)
    Board findWithUserNickById(Long boardId);	
}