package org.example.gfgspringboot.services;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev") // application-prd, application-qal
@Service(value="calculatoprServiceImplementation2")
public class CalculatoprServiceImplementation2 implements CalculatorService {

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




// CalculatoprServiceImplementation2