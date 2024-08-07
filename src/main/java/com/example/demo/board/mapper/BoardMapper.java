package com.example.demo.board.mapper;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.dto.SearchBoardDto;
import com.example.demo.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class BoardMapper {
    public static SearchBoardDto toDto(Board board) {
        return new SearchBoardDto(
                board.getBoardId(),
                board.getBoardTitle(),
                board.getBoardContent(),
                board.getBoardNickname(),
                board.getRegDate()
        );
    }

    public static List<SearchBoardDto> toDtoList(List<Board> boards) {
        return boards.stream()
                .map(BoardMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<SearchBoardDto> toDtoPage(Page<Board> boards, Pageable pageable) {
        List<SearchBoardDto> dtoList = toDtoList(boards.getContent());
        return new PageImpl<>(dtoList, pageable, boards.getTotalElements());
    }
}
