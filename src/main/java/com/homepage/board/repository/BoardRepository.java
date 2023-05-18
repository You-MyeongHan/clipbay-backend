package com.homepage.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.homepage.board.entity.Board;
import com.homepage.user.entity.User;

public interface BoardRepository extends JpaRepository<Board, Long>{
//	Page<Board> findAllOrderByIdDesc(Pageable pageable);
}