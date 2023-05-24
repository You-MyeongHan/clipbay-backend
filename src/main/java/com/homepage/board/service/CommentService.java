package com.homepage.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homepage.board.entity.Board;
import com.homepage.board.entity.Comment;
import com.homepage.board.repository.BoardRepository;
import com.homepage.board.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    
    @Transactional
    public Comment addCommentToBoard(Long boardId, String content) {
    	Board board=boardRepository.findById(boardId).orElse(null);
    	
    	if(board!=null) {
    		Comment comment= new Comment();
    		comment.setContent(content);
    		comment.setBoard(board);
    		return commentRepository.save(comment);
    	}
    	return null;
    }
    
    public List<Comment> findByBoard(Board board){
    	return commentRepository.findByBoard(board);
    }
    
    @Transactional
    public void deleteComment(Long commentId) {
    	Comment comment=commentRepository.findById(commentId)
    			.orElseThrow(()-> new IllegalArgumentException("Comment not found with ID: " + commentId));
    	commentRepository.delete(comment);
    }
}