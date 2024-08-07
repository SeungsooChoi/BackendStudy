package com.example.demo.board.controller;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.dto.SearchBoardDto;
import com.example.demo.board.service.BoardConvertService;
import com.example.demo.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardConvertService boardConvertService;

//    @GetMapping
//    public List<BoardDto> getAllBoard(){
//        return boardConvertService.convertAllToDto();
//    }

    @GetMapping
    public Page<SearchBoardDto> searchBoards(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String nickname,
            Pageable pageable) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        return boardService.search(title, content, startDateTime, endDateTime, nickname, pageable);
    }

    @GetMapping("/{boardId}")
    public BoardDto getBoard(@PathVariable Integer boardId){
        return boardConvertService.convertToDto(boardId);
    }

    @PostMapping
    public Integer createBoard(@RequestBody BoardDto boardDto){
        return boardService.createBoard(boardDto);
    }

    @PutMapping("/{boardId}")
    public Integer updateBoard(@PathVariable Integer boardId, @RequestBody BoardDto boardDto) {
        return boardService.updateBoard(boardId, boardDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Integer boardId) {
        boolean isDeleted =  boardService.deleteBoard(boardId);
        if(isDeleted){
            return ResponseEntity.ok("삭제 성공");
        } else {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }
}
