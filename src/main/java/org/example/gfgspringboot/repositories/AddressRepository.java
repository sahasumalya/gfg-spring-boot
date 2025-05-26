package org.example.gfgspringboot.repositories;

import org.example.gfgspringboot.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
