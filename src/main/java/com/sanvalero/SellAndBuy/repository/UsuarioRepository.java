package com.sanvalero.SellAndBuy.repository;

import com.sanvalero.SellAndBuy.domain.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

}
