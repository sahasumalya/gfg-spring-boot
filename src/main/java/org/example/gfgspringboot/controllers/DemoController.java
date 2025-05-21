package org.example.gfgspringboot.controllers;

import jakarta.annotation.Nullable;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.example.gfgspringboot.annotations.JsonSerializableField;
import org.example.gfgspringboot.models.Employee;
import org.example.gfgspringboot.models.EmployeeRequestBody;
import org.example.gfgspringboot.models.EmployeeResponseBody;
import org.example.gfgspringboot.models.Student;
import org.example.gfgspringboot.services.CalculatorService;
import org.example.gfgspringboot.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class DemoController {



    private CalculatorService calculatorService;

    @Autowired
    private EmployeeService employeeService;

    public DemoController(@Qualifier("calculatoprServiceImplementation2") CalculatorService calculatorService) {
        System.out.println("calculatoprServiceImplementation2 creating");
        this.calculatorService = calculatorService;
    }

    /*@GetMapping("/hello/{country}")
    public String sayHello(@RequestParam @Nullable String name,  @PathVariable(value="country") String state) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Student student = new Student(1, "xyz");
        Class<?> cls = student.getClass();
        Object obj  = cls.getMethod("getUpperCaseName").invoke(student, "Mr");
        return "Hello "+ state ;
    }*/

    @RequestMapping(method = RequestMethod.POST, path="/student_details")
    public ResponseEntity<Student> insertStudent(@RequestBody  Student student, @RequestHeader Map<String, String> headers) {
            MultiValueMap<String, String> hashMap = new LinkedMultiValueMap<>();
            //hashMap.put("tid", List.of(headers.get("tid")));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("tid", headers.get("tid"));
            return ResponseEntity.ok().headers(httpHeaders).body(student);
            //ResponseEntity<Student> responseEntity = new ResponseEntity<>(student, hashMap, HttpStatus.ACCEPTED);
            //return responseEntity;

    }

    @PostMapping("/saveEmployee")
    public ResponseEntity<EmployeeResponseBody> saveEmployee(@RequestBody EmployeeRequestBody employee) {
        Employee employeeDao = new Employee(employee.getName(), employee.getCompany());
        employeeDao = employeeService.save(employeeDao);


        return ResponseEntity.ok().body(new EmployeeResponseBody(employeeDao.getId(), employeeDao.getName(), employeeDao.getCompany()));
    }

    @GetMapping("/getEmployees")
    public ResponseEntity<List<Employee>> getEmployees(@RequestBody EmployeeRequestBody employee) {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/calculate")
    public ResponseEntity<String> calculate(@RequestParam int a, @RequestParam int b, @RequestParam String operation) {
            if(operation.equals("add")) {
                return ResponseEntity.ok(Integer.toString(calculatorService.add(a,b)));
            }
            return ResponseEntity.ok("invalid operation");
    }

    /*@PostMapping("/hello/{id}")
    public ResponseEntity<String> postHello(@RequestBody String body, @Nullable @RequestParam(value="p") String name, @PathVariable String id) throws NoSuchFieldException {

        return ResponseEntity.internalServerError().body("Hello " + name + " " + id);
    }*/



}
