package com.example.demo.board.controller;

import com.example.demo.board.dto.CommentDto;
import com.example.demo.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public CommentDto createComment(@PathVariable Integer boardId, @RequestBody CommentDto commentDto){
        return commentService.createComment(boardId, commentDto);
    }

    @PutMapping("/{commentId}")
    public CommentDto updateComment(@PathVariable Integer commentId, @RequestBody CommentDto commentDto){
        return commentService.updateComment(commentId, commentDto.getCommentContent());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId){
        Boolean isDeleted = commentService.deleteComment(commentId);
        if(isDeleted){
            return ResponseEntity.ok("삭제 성공");
        } else {
            return ResponseEntity.status(404).body("댓글을 찾을 수 없습니다.");
        }
    }
}
