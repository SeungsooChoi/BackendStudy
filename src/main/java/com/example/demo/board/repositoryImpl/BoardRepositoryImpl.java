package com.example.demo.board.repositoryImpl;

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

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BoardRepositoryImpl implements CustomBoardRepository {
    private final JPAQueryFactory queryFactory;


    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Board> search(String title, String content, LocalDateTime startDate, LocalDateTime endDate, String nickname, Pageable pageable) {
        QBoard board = QBoard.board;

        QueryResults<Board> results = queryFactory.selectFrom(board)
                .where(
                        titleContains(title),
                        contentContains(content),
                        createdAtBetween(startDate, endDate),
                        nicknameEq(nickname)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.regDate.desc())
                .fetchResults();

        List<Board> contentList = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(contentList, pageable, total);
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? QBoard.board.boardTitle.contains(title) : null;
    }

    private BooleanExpression contentContains(String content) {
        return content != null ? QBoard.board.boardContent.contains(content) : null;
    }

    private BooleanExpression createdAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
//        TODO : 여기서 LocalDate -> LocalDateTime 변경
        return QBoard.board.regDate.between(startDate, endDate);
    }

    private BooleanExpression nicknameEq(String author) {
        return author != null ? QBoard.board.boardNickname.eq(author) : null;
    }
}
