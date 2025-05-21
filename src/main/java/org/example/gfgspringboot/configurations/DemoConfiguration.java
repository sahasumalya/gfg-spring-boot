package org.example.gfgspringboot.configurations;


import org.example.gfgspringboot.models.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class DemoConfiguration {

    @Bean
    public Student getStudent() {
        return new Student(1, "xyz");
    }
}
/**
 * FATAL
 * ERROR
 * WARN
 * INFO
 * DEBUG
 * TRACE
 *
 */
