package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.domain.User;
import com.sanvalero.SellAndBuy.domain.dto.ProductDTO;
import com.sanvalero.SellAndBuy.exception.ProductNotFoundException;
import com.sanvalero.SellAndBuy.exception.UserNotFoundException;
import com.sanvalero.SellAndBuy.repository.ProductRepository;
import com.sanvalero.SellAndBuy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

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
     * @param productDTO ProductDTO object that you want to save
     * @return Product object
     */
    @Override
    public Product addProduct(long userId, ProductDTO productDTO) {
        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setCategory(productDTO.getCategory());
        newProduct.setSize(productDTO.getSize());
        newProduct.setNew(productDTO.isNew());
        newProduct.setRegisterDate(LocalDate.now());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        newProduct.setUserSeller(user);
        newProduct = productRepository.save(newProduct);

        return newProduct;
    }

    /**
     * Service that edits a saved product identified by its id
     * @param id Id of the product you want to edit
     * @param productDTO edited ProductDTO object
     * @return updated Product object
     */
    @Override
    public Product updateProduct(long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());
        product.setSize(productDTO.getSize());
        product.setNew(productDTO.isNew());

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
