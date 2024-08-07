package com.example.demo.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @JsonIgnore
    @JoinColumn(name = "board_id")
    @ManyToOne
    private Board board;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Column(nullable = false)
    private String commentContent;

    @Column(nullable = false)
    private String commentNickname;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setCommentNickname(String commentNickname) {
        this.commentNickname = commentNickname;
    }
    public void setChildren(List<Comment> children) {
        this.children = children;
    }
}
