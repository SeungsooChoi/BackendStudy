package com.example.demo.board.repository;

import com.example.demo.board.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface CustomBoardRepository {
    Page<BoardDto> findAllWithPagingAndFilters(String title, String content, String author, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
