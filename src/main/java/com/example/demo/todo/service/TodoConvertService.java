package com.example.demo.todo.service;

import com.example.demo.todo.dto.TodoDto;
import com.example.demo.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoConvertService {
    private final TodoService todoService;

    public List<TodoDto> convertAllToDto(){
        List<Todo> todos = todoService.getAll();

        return todos.stream().map(todo -> convertToDto(todo.getTodoId())).collect(Collectors.toList());
    }

    public TodoDto convertToDto(Integer id){
        Todo todo = todoService.getById(id);

        TodoDto todoDto = new TodoDto();
        todoDto.setTodo(todo.getTodoId(), todo.getTodoText(), todo.getCompleteYn());

        return todoDto;
    }
}
