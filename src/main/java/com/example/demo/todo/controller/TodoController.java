package com.example.demo.todo.controller;

import com.example.demo.todo.dto.TodoDto;
import com.example.demo.todo.service.TodoConvertService;
import com.example.demo.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    private final TodoConvertService todoConvertService;

    @GetMapping
    public List<TodoDto> getAllTodos(){
        return todoConvertService.convertAllToDto();
    }

    @GetMapping("/{todoId}")
    public TodoDto getTodo(@PathVariable Integer todoId){
        return todoConvertService.convertToDto(todoId);
    }

    @PostMapping
    public Integer createTodo(@RequestBody TodoDto todoDto){
        return todoService.createTodo(todoDto);
    }

    @PutMapping("/{todoId}")
    public Integer updateTodo(@PathVariable Integer todoId, @RequestBody TodoDto todoDto){
        return todoService.updateTodo(todoId, todoDto);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable Integer todoId){
        boolean isDeleted = todoService.deleteTodo(todoId);
        if(isDeleted){
            return ResponseEntity.ok("삭제 성공");
        } else {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }
}
