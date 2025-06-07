package org.example.gfgspringboot.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.gfgspringboot.services.EmployeeListener;
import org.ietf.jgss.GSSName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//@EntityListeners(EmployeeListener.class)
@Slf4j
@Entity
@Table(name="employees")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    private String name;

    @Transient
    private String displayName;

    @ManyToMany(fetch = FetchType.EAGER)
   /* @JoinTable(@Table(name = "employee_projects"),
            @JoinColumn(columnName="employee_id", inverse_column="project_id")*/
   /* @JoinTable(
            name = "employee_projects",
            joinColumns = @Column(name = "employee_id")
            inverseJoinColumns = @Column(name = "project_id")
    )
    private List<Project> projects;

    /*@Embedded
    @AttributeOverrides({
            @AttributeOverride(name="email", column = @Column(name = "employeeEmail")),
            @AttributeOverride(name="phone", column = @Column(name = "employeePhone")),
    })
    private ContactInfo contactInfo;*/

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name="address_id", referencedColumnName = "id")
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /*@Temporal(TemporalType.DATE)
    private Date createdAt;*/

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDisplayName() {
        return gender.equals(Gender.MALE) ? "Mr."+name : "Mrs."+name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="company_id", referencedColumnName = "id")
    private Company company;


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }




    public Employee() {

    }
}
