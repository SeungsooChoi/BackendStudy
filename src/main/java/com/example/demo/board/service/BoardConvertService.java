package com.example.demo.board.service;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.dto.CommentDto;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardConvertService {
    private final BoardService boardService;

    public List<BoardDto> convertAllToDto(){
        List<Board> boards = boardService.getAll();

        return boards.stream().map(board -> convertToDto(board.getBoardId())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardDto convertToDto(Integer id){
        Board board = boardService.getById(id);

        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setBoardTitle(board.getBoardTitle());
        boardDto.setBoardContent(board.getBoardContent());
        boardDto.setBoardNickname(board.getBoardNickname());
        boardDto.setComments(board.getCommentList().stream()
                .filter(comment -> comment.getParent() == null) // 최상위 댓글만 필터링
                .map(this::convertToDto)
                .collect(Collectors.toList()));
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
