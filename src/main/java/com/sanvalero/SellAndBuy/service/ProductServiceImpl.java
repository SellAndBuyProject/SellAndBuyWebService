package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.exception.ProductNotFoundException;
import com.sanvalero.SellAndBuy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Service that searches for all products
     * @return Set of Product objects
     */
    @Override
    public Set<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Service that search for a product identified by id
     * @param id product identifier
     * @return Product object
     */
    @Override
    public Product findById(long id) {
        return productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * Service that returns the list of product-type objects of a certain category
     * @param category category of the products you want to search for
     * @return list of objects of type Product
     */
    public List<Product> findByCategory(String category) {
        return productRepository.findAll().stream()
                .filter(producto -> producto.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    /**
     * Service that returns the list of Product objects with a certain name
     * @param name name of the products you want to search for
     * @return list of objects of type Product
     */
    public List<Product> findByName(String name) {
        return productRepository.findAll().stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    /**
     * Service that saves a new product
     * @param product Product object that you want to save
     * @return Product object
     */
    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Service that edits a saved product identified by its id
     * @param id Id of the product you want to edit
     * @param product edited Product object.
     * @return updated Product object
     */
    @Override
    public Product updateProduct(long id, Product product) {
        Product saveProduct = productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(saveProduct);
        return productRepository.save(product);
    }

    /**
     * Service that deletes a saved product identified by its id
     * @param id Id of the product you want to remove.
     */
    @Override
    public void deleteProduct(long id) {
        Product saveProduct = productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(id);
    }
}
