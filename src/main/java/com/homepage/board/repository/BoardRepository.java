package com.homepage.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homepage.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
}