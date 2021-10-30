package com.sanvalero.SellAndBuy.repository;

import com.sanvalero.SellAndBuy.domain.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Integer> {

    Set<Producto> findAll();

}
