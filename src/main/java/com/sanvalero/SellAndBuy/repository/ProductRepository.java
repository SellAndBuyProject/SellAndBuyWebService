package com.sanvalero.SellAndBuy.repository;

import com.sanvalero.SellAndBuy.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Set<Product> findAll();


}
