package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Producto;
import com.sanvalero.SellAndBuy.domain.Usuario;
import com.sanvalero.SellAndBuy.domain.Wishlist;
import com.sanvalero.SellAndBuy.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductoService productoService;

    /**
     * Servicio que busca la wishlist de un usuario
     * @param idUsuario identificador del usuario
     * @return objeto wishlist encontrado
     */
    @Override
    public Wishlist findbyUsuario(int idUsuario) {
        return wishlistRepository.findByUsuarioId(idUsuario);
    }

    /**
     * Servicio que permite al usuario añadir un producto
     * @param usuario objeto usuario
     * @param producto objeto producto
     * @return wishlist actualizada
     */
    @Override
    public Wishlist addProducto(Usuario usuario, Producto producto) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUsuario(usuario);
        wishlist.addProducto(producto);

        return wishlistRepository.save(wishlist);
    }

    /**
     * Servicio que permite al usuario eliminar un producto de su wishlist
     * @param idUsuario identificador del usuario
     * @param idProducto identificador del producto que se quiere eliminar
     */
    @Override
    public void deleteProducto(int idUsuario, int idProducto) {
        Wishlist wishlist = wishlistRepository.findByUsuarioId(idUsuario);
        Producto producto = productoService.findById(idProducto);

        List<Producto> productos = wishlist.getProductos();
        productos.remove(producto);
    }
}
