package com.example.demo.board.repository;

import com.example.demo.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface CustomBoardRepository {
    Page<Board> search(String title, String content, LocalDateTime startDate, LocalDateTime endDate, String nickname, Pageable pageable);
}
