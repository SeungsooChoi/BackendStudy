package com.example.demo.board.service;

import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.dto.CommentDto;
import com.example.demo.board.entity.Comment;
import com.example.demo.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final BoardConvertService boardConvertService;

    public CommentDto createComment(Integer boardId, CommentDto commentDto){
        Comment comment = new Comment();
        comment.setCommentId(commentDto.getCommentId());
        comment.setCommentContent(commentDto.getCommentContent());
        comment.setCommentNickname(commentDto.getCommentNickname());

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        comment.setBoard(board);

        if(commentDto.getParentId() != null){
            Comment parent = commentRepository.findById(commentDto.getParentId()).orElseThrow(() ->  new IllegalArgumentException("올바른 부모 comment Id가 아님"));
            comment.setParent(parent);
        }

        Comment createdComment = commentRepository.save(comment);
        return boardConvertService.convertToDto(createdComment);
    }

    public CommentDto updateComment(Integer commentId, String content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        comment.setCommentContent(content); // 내용

        Comment updatedComment = commentRepository.save(comment);
        return boardConvertService.convertToDto(updatedComment);
    }

    public Boolean deleteComment(Integer id) {
        if(commentRepository.existsById(id)){
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
