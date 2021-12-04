package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.domain.User;
import com.sanvalero.SellAndBuy.domain.dto.UserDTO;
import com.sanvalero.SellAndBuy.exception.*;
import com.sanvalero.SellAndBuy.repository.ProductRepository;
import com.sanvalero.SellAndBuy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Service that searches for all users
     * @return Set of objects of type User
     */
    @Override
    public Set<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Service search users by name
     * @param name Name of the user you want to search for
     * @return User object
     */
    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    /**
     * Service that searches for users by email and password to log in.
     * @param email user's email
     * @param password user's password
     * @return User object
     */
    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(UnauthorizedException::new);
    }

    /**
     * Service that add a user
     * @param userDTO UserDTO object
     * @return User object
     */
    @Override
    public User addUser(UserDTO userDTO) {
        User newUser = new User();
        if (userRepository.existsByName(userDTO.getName()))
            throw new UserDuplicateException("nombre: " + userDTO.getName());

        if (userRepository.existsByEmail(userDTO.getEmail()))
            throw new UserDuplicateException("email: " + userDTO.getEmail());

        if (userDTO.getName().isEmpty() || userDTO.getEmail().isEmpty() || userDTO.getPassword().isEmpty())
            throw new UserMissingDataException();

        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        newUser.setFavStyle(userDTO.getFavStyle());
        newUser.setFavColor(userDTO.getFavColor());
        newUser.setSize(userDTO.getSize());

        return userRepository.save(newUser);
    }

    /**
     * Service that allows the user to add a product
     * @param userId user identifier
     * @param productId product identifier
     * @return updated user object
     */
    @Override
    public User addProductToWishlist(long userId, long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ProductNotFoundException(productId));

        List<Product> wishlist = user.getWishlist();
        // If wishlist does not contains the product & the product is not sold by this user
        if(!wishlist.contains(product) && !user.getProducts().contains(product)) {
            user.addProductToWishlist(product); // Add product to wishlist
        }
        else if (wishlist.contains(product)) {
            throw new ProductDuplicateException(product.getId());
        }

        return userRepository.save(user);
    }

    /**
     * Service that allows the user to remove a product from their wishlist
     * @param userId user identifier
     * @param productId Identifier of the product you want to remove
     */
    @Override
    public void deleteProductFromWishlist(long userId, long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Product> wishlist = user.getWishlist();

        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ProductNotFoundException(productId));

        wishlist.remove(product);
        userRepository.save(user);
    }

    /**
     * Add a product to the user's history
     * @param userId user identifier
     * @param productId Product identifier that the user has seen
     * @return Product object
     */
    @Override
    public User addProductToHistory(long userId, long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Product productConsulted = productService.findById(productId);

        List<Product> history = user.getHistory();
        if(history.size() >= 10) {
            productRepository.delete(history.get(0));
        }
        // If history does not contains the product & the product is not sold by this user
        if(!history.contains(productConsulted) && !user.getProducts().contains(productConsulted)) {
            user.addProductToHistory(productConsulted); // Add product to history
        }
        else if(history.contains(productConsulted)){
            throw new ProductDuplicateException(productConsulted.getId());
        }

        return userRepository.save(user);
    }

    /**
     * Service that allows you to get a user's history
     * @param userId user identifier
     * @return list of objects of type Product
     */
    @Override
    public List<Product> findHistory(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return user.getHistory();
    }

    /**
     * Service that allows the user to modify his name
     * @param id user identifier
     * @param newName new name
     * @return User object
     */
    @Override
    public User changeName(long id, String newName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setName(newName);
        return userRepository.save(user);
    }

    /**
     * Service that allows the user to modify his password
     * @param id user identifier
     * @param oldPassword old password
     * @param newPassword new password
     * @return User object
     */
    @Override
    public User changePass(long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.getPassword().equals(oldPassword))
            throw new UnauthorizedException();

        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    /**
     * Service that allows you to delete the user
     * @param id user identifier
     * @param password user password
     */
    @Override
    public void deleteUser(long id, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.getPassword().equals(password))
            throw new UnauthorizedException();

        userRepository.delete(user);
    }
}
