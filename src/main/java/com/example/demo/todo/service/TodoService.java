package com.example.demo.todo.service;

import com.example.demo.todo.dto.TodoDto;
import com.example.demo.todo.entity.Todo;
import com.example.demo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public Todo getById(int id){
        return todoRepository.findById(id).orElse(null);
    }

    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    public Integer createTodo(TodoDto todoDto){
        Todo todo = new Todo();
        todo.setTodo(todoDto.getTodoId(), todoDto.getTodoText(), todoDto.getCompleteYn());
        todo = todoRepository.save(todo);

        return todo.getTodoId();
    }

    public Integer updateTodo(Integer id, TodoDto todoDto){
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("투두를 찾을 수 없습니다."));
        todo.setTodo(todoDto.getTodoText(), todoDto.getCompleteYn());
        todo = todoRepository.save(todo);

        return todo.getTodoId();
    }

    public boolean deleteTodo(Integer id){
        if(todoRepository.existsById(id)){
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
