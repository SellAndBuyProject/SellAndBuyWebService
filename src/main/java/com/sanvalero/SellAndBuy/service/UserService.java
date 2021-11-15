package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Product;
import com.sanvalero.SellAndBuy.domain.User;
import com.sanvalero.SellAndBuy.domain.dto.UserDTO;

import java.util.List;
import java.util.Set;

public interface UserService {

    Set<User> findAll();
    User findById(long id);
    User findByName(String name);
    User findByEmailAndPassword(String email, String password);
    User addUser(UserDTO userDTO);
    User addProductToWishlist(long userId, long productId);
    void deleteProductFromWishlist(long userId, long productId);
    User addProductToHistory(long userId, long productId);
    List<Product> findHistory(long userId);
    User changeName(long id, String newName);
    User changePass(long id, String oldPassword, String newPassword);
    void deleteUser(long id, String password);
}
