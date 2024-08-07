package com.example.demo.calculator.controller;

import com.example.demo.calculator.dto.CalculatorDto;
import com.example.demo.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculate")
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;

    @PostMapping
    public Integer createExecute(@RequestBody CalculatorDto calculatorDto){
        return calculatorService.createCalculator(calculatorDto);
    }
}
