package com.homepage.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homepage.board.entity.Board;
import com.homepage.board.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByBoard(Board board);
}