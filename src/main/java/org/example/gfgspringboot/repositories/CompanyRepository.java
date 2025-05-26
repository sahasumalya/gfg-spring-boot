package org.example.gfgspringboot.repositories;

import org.example.gfgspringboot.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(name="select u from Company u where u.name = ?1",nativeQuery=true)
    public Company findByName(String name);
}
