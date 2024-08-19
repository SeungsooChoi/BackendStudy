package com.example.demo.board.repositoryImpl;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.QBoard;
import com.example.demo.board.repository.CustomBoardRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class BoardRepositoryImpl implements CustomBoardRepository {
    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BoardDto> findAllWithPagingAndFilters(String title, String content, String author, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        QBoard board = QBoard.board;

        QueryResults<Board> results = queryFactory.selectFrom(board)
                .where(
                        titleContains(title),
                        contentContains(content),
                        authorContains(author),
                        dateBetween(startDate, endDate) // 필수 조건
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.regDate.desc())
                .fetchResults();

        List<BoardDto> boardList = results
                .getResults()
                .stream()
                .map(this::toDTO)
                .toList();

        long total = results.getTotal();

        return new PageImpl<>(boardList, pageable, total);
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? QBoard.board.boardTitle.containsIgnoreCase(title) : null;
    }

    private BooleanExpression contentContains(String content) {
        return content != null ? QBoard.board.boardContent.containsIgnoreCase(content) : null;
    }

    private BooleanExpression authorContains(String author) {
        return author != null ? QBoard.board.boardNickname.containsIgnoreCase(author) : null;
    }

    private BooleanExpression dateBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        if (startDateTime != null && endDateTime != null) {
            return QBoard.board.regDate.between(startDateTime, endDateTime);
        } else if (startDateTime != null) {
            return QBoard.board.regDate.goe(startDateTime);
        } else if (endDateTime != null) {
            return QBoard.board.regDate.loe(endDateTime);
        } else {
            return null;
        }

    }

    private BoardDto toDTO(Board board) {
        BoardDto dto = new BoardDto();
        dto.setBoardDto(board.getBoardId(), board.getBoardTitle(), board.getBoardContent(), board.getBoardNickname(), board.getRegDate());
        return dto;
    }
}
