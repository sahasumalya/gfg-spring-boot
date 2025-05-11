package org.example.gfgspringboot.controllers;

import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World!";
    }

    @PostMapping("/hello/{id}")
    public ResponseEntity<String> postHello(@RequestBody String body, @Nullable @RequestParam(value="p") String name, @PathVariable String id) {
        return ResponseEntity.internalServerError().body("Hello " + name + " " + id);
    }



}
