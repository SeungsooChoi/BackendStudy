package com.example.demo.board.service;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.dto.CommentDto;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.Comment;
import com.example.demo.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardConvertService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public BoardDto convertToDto(Integer id){
        Board board = boardRepository.findById(id).orElse(null);

        BoardDto boardDto = new BoardDto();
        boardDto.setBoardDto(
                board.getBoardId(),
                board.getBoardTitle(),
                board.getBoardContent(),
                board.getBoardNickname(),
                board.getCommentList().stream()
                        .filter(comment -> comment.getParent() == null) // 최상위 댓글만 필터링
                        .map(this::convertToDto)
                        .collect(Collectors.toList())
        );
        return boardDto;
    }

    public CommentDto convertToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setCommentContent(comment.getCommentContent());
        commentDto.setParentId(comment.getParent() != null ? comment.getParent().getCommentId() : null);
        commentDto.setBoardId(comment.getBoard().getBoardId());
        commentDto.setChildren(comment.getChildren().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
        return commentDto;
    }
}
