package com.example.demo.board.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "board")
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB마다 다른 autoincrement 정책을 따라 가겠다
    private int boardId;

    @Column(nullable = false)
    private String boardTitle;

    @Column
    private String boardContent;

    @Column(nullable = false)
    private String boardNickname;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;

    public void setBoardContent(String title, String content){
        this.boardTitle = title;
        this.boardContent = content;
    }
    public void setBoardContent(String title, String content, String author){
        this.boardTitle = title;
        this.boardContent = content;
        this.boardNickname = author;
    }
}
