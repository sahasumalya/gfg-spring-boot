package org.example.gfgspringboot.services;

import org.example.gfgspringboot.models.Address;
import org.example.gfgspringboot.models.Company;
import org.example.gfgspringboot.models.Employee;
import org.example.gfgspringboot.repositories.AddressRepository;
import org.example.gfgspringboot.repositories.CompanyRepository;
import org.example.gfgspringboot.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, CompanyRepository companyRepository, AddressRepository addressRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

    public Employee save(Employee employee) {
        Company company = companyRepository.findByName(employee.getCompany().getName());
        company.setName("Oracle");
        // addressRepository.save(employee.getAddress());
      /*  if (company == null) {
            company = new Company();
            company.setName(employee.getCompany().getName());
            companyRepository.save(company);
        }*/
        employee.setCompany(company);
        employee.getCompany().setId(company.getId());
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployeesOfCompany(String companyName) {
        return companyRepository.findByName(companyName).getEmployees();
    }

    public List<Employee> findAll() {

        return employeeRepository.findAll();
    }
}
