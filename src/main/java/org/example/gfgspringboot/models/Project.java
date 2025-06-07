package org.example.gfgspringboot.models;

import jakarta.persistence.*;

import java.util.List;

//@Entity
//@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    /*@ManyToMany(mappedBy = "projects")
    private List<Employee> employees;*/


}
