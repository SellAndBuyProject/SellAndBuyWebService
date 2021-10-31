package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Producto;
import com.sanvalero.SellAndBuy.domain.Wishlist;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public interface WishlistService {

    Wishlist findbyUsuario(int idUsuario);
    Wishlist addProducto(int idUsuario, Producto producto);
    void deleteProducto(int idUsuario, int idProducto);
}
