package org.example.gfgspringboot.services;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

@Component
public class EmployeeListener {

    @PrePersist
    public void logData(){
        System.out.println("student entitu is going to persist");
    }

    @PostPersist
    public void saveData(){
        System.out.println("student entitu has been persisted");
    }
}
