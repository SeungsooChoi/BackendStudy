package com.example.demo.calculator.repository;

import com.example.demo.calculator.entity.Calculator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculatorRepository extends JpaRepository<Calculator, Integer> {
}
