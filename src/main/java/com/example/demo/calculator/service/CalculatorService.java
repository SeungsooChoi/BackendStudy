package com.example.demo.calculator.service;

import com.example.demo.calculator.util.Execute;
import com.example.demo.calculator.dto.CalculatorDto;
import com.example.demo.calculator.entity.Calculator;
import com.example.demo.calculator.repository.CalculatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    private final CalculatorRepository calculatorRepository;

    public Calculator getById(int id) {
        return calculatorRepository.findById(id).orElse(null);
    }

    @Transactional
    public Integer createCalculator(CalculatorDto calculatorDto){
        Calculator calculator = new Calculator();
        calculator.setCalculatorId(calculatorDto.getCalculatorId());
        calculator.setCalculatorFormula(calculatorDto.getCalculatorFormula());

        Integer answer = calculate(calculatorDto);
        calculator.setCalculatorAnswer(answer);
        calculatorRepository.save(calculator);

        return answer;
    }

    private Integer calculate(CalculatorDto calculatorDto){
        Execute execute = new Execute();
        String expression = calculatorDto.getCalculatorFormula();

        return execute.evaluate(expression);
    }
}
