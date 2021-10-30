package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Producto;

import java.util.Set;

public interface ProductoService {

    Set<Producto> findAll();
    Producto findById(int id);
    Producto addProducto(Producto producto);
    Producto updateProducto(int id, Producto producto);
    void deleteProducto(int id);

}
