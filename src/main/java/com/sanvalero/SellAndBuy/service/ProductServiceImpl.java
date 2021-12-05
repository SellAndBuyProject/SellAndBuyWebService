package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.domain.User;
import com.sanvalero.SellAndBuy.domain.dto.ProductDTO;
import com.sanvalero.SellAndBuy.exception.NotImplementedException;
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
                orElseThrow(ProductNotFoundException::new);
    }

    /**
     * Service that returns the list of product-type objects of a certain category
     * @param category category of the products you want to search for
     * @return list of objects of type Product
     */
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    /**
     * Service that returns the list of Product objects that contain a certain name,
     * regardless of uppercase or lowercase.
     * @param name name of the products you want to search for (Can be one or two words)
     *             If the name has more than two words, it returns an exception
     * @return list of objects of type Product
     */
    public List<Product> findByName(String name) {
        // If the name contains more than two words
        if (name.split(" ").length > 2)
            throw new NotImplementedException();

        // If the name contains two words
        if (name.split(" ").length == 2) {
            return productRepository.findAll().stream()
                    // Create a first stream with the products that match the first word
                    .filter(product ->
                            product.getName()
                                    .toLowerCase()
                                    // Split name into the array that will have as many objects as words contains the string
                                    // In this case, we get the first object (the first word)
                                    .contains(name.split(" ")[0].toLowerCase()))
                    // Create a second stream with the products that are in the first stream and that match the second word
                    .filter(product ->
                            product.getName()
                                    .toLowerCase()
                                    // Split name into the array that will have as many objects as words contains the string
                                    // In this case, we get the second object (the second word)
                                    .contains(name.split(" ")[1].toLowerCase()))
                    .collect(Collectors.toList());
        }

        // If the name contains only one word
        return productRepository.findAll().stream()
                .filter(product ->
                        product.getName()
                                .toLowerCase()
                                .contains((name).toLowerCase()))
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
        newProduct.setImage(productDTO.getImage());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setCategory(productDTO.getCategory());
        newProduct.setSize(productDTO.getSize());
        newProduct.setNew(productDTO.isNew());
        newProduct.setSold(false);
        newProduct.setRegisterDate(LocalDate.now());

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

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
                orElseThrow(ProductNotFoundException::new);

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
        productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.deleteById(id);
    }
}
