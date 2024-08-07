package com.example.demo.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "todo")
public class Todo {
    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int todoId;

    @Column
    private String todoText;

    @Column
    private String completeYn;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;

    public void setTodo(int todoId, String todoText, String completeYn){
        this.todoId = todoId;
        this.todoText = todoText;
        this.completeYn = completeYn;
    }

    public void setTodo(String todoText, String completeYn){
        this.todoText = todoText;
        this.completeYn = completeYn;
    }
}
