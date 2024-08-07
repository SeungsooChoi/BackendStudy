package com.example.demo.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBoardDto {
    private int boardId;
    private String boardTitle;
    private String boardContent;
    private String boardNickname;
    private LocalDateTime regDate;
}
