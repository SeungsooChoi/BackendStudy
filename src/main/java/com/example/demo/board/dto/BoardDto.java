package com.example.demo.board.dto;

import com.querydsl.core.annotations.QueryProjection;
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

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public void setBoardNickname(String boardNickname) {
        this.boardNickname = boardNickname;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
