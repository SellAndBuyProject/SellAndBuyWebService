package com.sanvalero.SellAndBuy.repository;

import com.sanvalero.SellAndBuy.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Set<User> findAll();
    User findByName(String name);
    Optional<User> findByEmailAndPassword(String email, String password);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
