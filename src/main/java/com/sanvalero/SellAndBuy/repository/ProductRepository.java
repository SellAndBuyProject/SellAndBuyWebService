package com.sanvalero.SellAndBuy.repository;

import com.sanvalero.SellAndBuy.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Set<Product> findAll();
    Product findByName(String name);
    List<Product> findByCategory(String category);
}
