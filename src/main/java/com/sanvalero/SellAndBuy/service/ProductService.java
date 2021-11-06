package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {

    Set<Product> findAll();
    Product findById(long id);
    List<Product> findByCategory(String category);
    List<Product> findByName(String name);
    Product addProduct(Product product);
    Product updateProduct(long id, Product product);
    void deleteProduct(long id);

}
