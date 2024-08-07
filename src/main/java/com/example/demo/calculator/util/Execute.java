package com.example.demo.calculator.util;

import java.util.Stack;

public class Execute {

    // 우선순위 설정을 위한 메소드
    private int getPrecedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    // 중위 표기법을 후위 표기법으로 변환하는 메소드
    public String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        int n = expression.length();

        for (int i = 0; i < n; i++) {
            char c = expression.charAt(i);

            // 피연산자가 여러 자리 수인 경우 처리
            if (Character.isDigit(c)) {
                while (i < n && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    result.append(expression.charAt(i++));
                }
                result.append(' ');
                i--;
            }
            // '('는 스택에 추가
            else if (c == '(') {
                stack.push(c);
            }
            // ')'는 '('를 만날 때까지 스택에서 팝
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(' ');
                }
                stack.pop();
            }
            // 연산자
            else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }

        // 스택에 남아있는 모든 연산자를 팝
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                return "Invalid Expression";
            }
            result.append(stack.pop()).append(' ');
        }
        return result.toString();
    }

    // 후위 표기법을 계산하는 메소드
    public Integer evaluatePostfix(String expression) {
        Stack<Integer> stack = new Stack<>();
        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            // 피연산자라면 스택에 추가
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Integer.parseInt(token));
            } else {
                // 연산자라면 두 피연산자를 팝하고 연산을 수행한 후 결과를 스택에 푸시
                Integer val1 = stack.pop();
                Integer val2 = stack.pop();

                switch (token) {
                    case "+":
                        stack.push(val2 + val1);
                        break;
                    case "-":
                        stack.push(val2 - val1);
                        break;
                    case "*":
                        stack.push(val2 * val1);
                        break;
                    case "/":
                        stack.push(val2 / val1);
                        break;
                }
            }
        }
        return stack.pop();
    }

    // 전체적인 평가 메소드
    public Integer evaluate(String expression) {
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }
}
