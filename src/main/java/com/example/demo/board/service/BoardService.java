package com.example.demo.board.service;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.dto.SearchBoardDto;
import com.example.demo.board.entity.Board;
import com.example.demo.board.mapper.BoardMapper;
import com.example.demo.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> getAll(){
        return boardRepository.findAll();
    }

    public Board getById(int id){
        return boardRepository.findById(id).orElse(null);
    }

    public Integer createBoard(BoardDto boardDto){
        Board board = new Board();
        board.setBoardId(boardDto.getBoardId());
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBoardContent(boardDto.getBoardContent());
        board.setBoardNickname(boardDto.getBoardNickname());
        board = boardRepository.save(board);

        return board.getBoardId();
    }

    public Integer updateBoard(Integer id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBoardContent(boardDto.getBoardContent());
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
    public Page<SearchBoardDto> search(String title, String content, LocalDateTime startDate, LocalDateTime endDate, String author, Pageable pageable) {
        Page<Board> boards = boardRepository.search(title, content, startDate, endDate, author, pageable);
        return BoardMapper.toDtoPage(boards, pageable);
    }
}
