package com.example.demo.board.service;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.dto.SearchBoardDto;
import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.repositoryImpl.BoardRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private static final Logger log = LoggerFactory.getLogger(BoardRepositoryImpl.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public Integer createBoard(BoardDto boardDto){
        Board board = new Board();
        board.setBoardContent(boardDto.getBoardTitle(), boardDto.getBoardContent(), boardDto.getBoardNickname());
        board = boardRepository.save(board);

        return board.getBoardId();
    }

    public Integer updateBoard(Integer id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        board.setBoardContent(boardDto.getBoardTitle(), boardDto.getBoardContent());
        board = boardRepository.save(board);

        return board.getBoardId();
    }

    public boolean deleteBoard(Integer id) {
        if(boardRepository.existsById(id)){
            boardRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Page<BoardDto> getAllBoards(SearchBoardDto searchBoardDto, Pageable pageable){
        // 페이지 번호가 0보다 작은 경우 0으로 설정, 그렇지 않으면 페이지 번호를 1 감소
        int page = (pageable.getPageNumber() > 0) ? pageable.getPageNumber() - 1 : 0;
        // 기존 Pageable의 페이지 사이즈와 정렬 정보를 재사용하여 새로운 Pageable 생성
        Pageable correctedPageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());

        // 문자열을 LocalDate로 변환
        LocalDate startDate = convertToLocalDate(searchBoardDto.getStartDate());
        LocalDate endDate = convertToLocalDate(searchBoardDto.getEndDate());

        log.info("searchBoardDto  {}", searchBoardDto.getStartDate(), searchBoardDto.getEndDate());
        log.info("startDate endDate {}", startDate, endDate);

        // 수정된 Pageable을 사용하여 Repository에서 페이징 처리된 결과 반환
        return boardRepository.findAllWithPagingAndFilters(
                searchBoardDto.getTitle(),
                searchBoardDto.getContent(),
                searchBoardDto.getAuthor(),
                startDate,
                endDate,
                correctedPageable);
    }

    // 문자열을 LocalDate로 변환하는 메서드
    private LocalDate convertToLocalDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;  // 빈 문자열이거나 null이면 null 반환
        }
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;  // 날짜 형식이 잘못된 경우 null 반환
        }
    }
}
