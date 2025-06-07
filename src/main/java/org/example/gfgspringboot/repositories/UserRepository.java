package org.example.gfgspringboot.repositories;

import org.example.gfgspringboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(name = "Select u from users u where username=?1 and password=?2", nativeQuery = true)
    User findByUsernameAndPassword(String username, String password);

    @Query(name = "Select u from users u where username=?1", nativeQuery = true)
    User findByUsername(String username);


    @Query(name = "Select u from users u where email=?1", nativeQuery = true)
    User findByEmail(String emai);

}
