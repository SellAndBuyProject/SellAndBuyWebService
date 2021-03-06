package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.domain.dto.ProductDTO;

import java.util.List;
import java.util.Set;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public interface ProductService {

    Set<Product> findAll();
    Product findById(long id);
    List<Product> findAllByUser(long userId);
    List<Product> findByCategory(String category);
    List<Product> findByName(String name);
    Product addProduct(long userId, ProductDTO productDTO);
    Product updateProduct(long id, ProductDTO productDTO);
    void deleteProduct(long id);

}
