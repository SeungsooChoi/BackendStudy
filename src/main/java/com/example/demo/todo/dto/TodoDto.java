package com.example.demo.todo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private int todoId;
    private String todoText;
    private String completeYn;

    public void setTodo(int todoId, String todoText, String completeYn){
        this.todoId = todoId;
        this.todoText = todoText;
        this.completeYn = completeYn;
    }
}
