package com.example.demo.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBoardDto {
    private String title;
    private String content;
    private String author;
    private String startDate;  // 필수
    private String endDate;    // 필수
}
