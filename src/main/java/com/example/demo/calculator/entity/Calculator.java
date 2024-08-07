package com.example.demo.calculator.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "calculator")
public class Calculator {
    @Id
    @Column(name = "calculator_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int calculatorId;

    @Column
    private String calculatorFormula;

    @Column
    private Integer calculatorAnswer;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    public void setCalculatorId(int calculatorId) {
        this.calculatorId = calculatorId;
    }

    public void setCalculatorFormula(String calculatorFormula) {
        this.calculatorFormula = calculatorFormula;
    }

    public void setCalculatorAnswer(Integer calculatorAnswer) {
        this.calculatorAnswer = calculatorAnswer;
    }
}
