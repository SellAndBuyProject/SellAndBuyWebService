package com.sanvalero.SellAndBuy.repository;

import com.sanvalero.SellAndBuy.domain.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    Set<Usuario> findAll();
    Optional<Usuario> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}
