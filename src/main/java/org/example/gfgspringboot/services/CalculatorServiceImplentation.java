package org.example.gfgspringboot.services;


import org.springframework.stereotype.Service;

@Service
public class CalculatorServiceImplentation implements CalculatorService {
    public CalculatorServiceImplentation() {
        System.out.println("CalculatorServiceImplentation creating bean");
    }

    public int add(int a, int b) {
        return a + b;
    }
    public int subtract(int a, int b) {
        return a - b;
    }
    public int multiply(int a, int b) {
        return a * b;
    }
    public int divide(int a, int b) {
        return a / b;
    }
}
