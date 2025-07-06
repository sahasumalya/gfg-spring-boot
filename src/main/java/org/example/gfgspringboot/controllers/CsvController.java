package org.example.gfgspringboot.controllers;

import org.example.gfgspringboot.models.EmployeeCSV;
import org.example.gfgspringboot.services.CsvParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CsvController {

    @Autowired
    private CsvParserService csvParserService;

    @GetMapping("/parse-csv")
    public List<EmployeeCSV> parseCsv() throws Exception {
        String filePath = "src/main/resources/employees.csv"; // Replace with your file path
        return csvParserService.parseCsvFile(filePath);
    }
}