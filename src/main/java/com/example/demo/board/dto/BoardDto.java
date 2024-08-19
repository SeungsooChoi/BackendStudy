package com.example.demo.board.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 풀방
public class BoardDto {
    private int boardId;
    private String boardTitle;
    private String boardContent;
    private String boardNickname;
    private List<CommentDto> comments;
    private LocalDateTime regDate;

    public void setBoardDto(int id, String title, String content, String author, LocalDateTime regDate){
        this.boardId = id;
        this.boardTitle = title;
        this.boardContent = content;
        this.boardNickname = author;
        this.regDate = regDate;
    }

    public void setBoardDto(int id, String title, String content, String author, List<CommentDto> comments){
        this.boardId = id;
        this.boardTitle = title;
        this.boardContent = content;
        this.boardNickname = author;
        this.comments = comments;
    }
}
