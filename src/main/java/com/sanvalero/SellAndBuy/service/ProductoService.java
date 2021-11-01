package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Producto;

import java.util.List;
import java.util.Set;

public interface ProductoService {

    Set<Producto> findAll();
    Producto findById(int id);
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByNombre(String nombre);
    Producto addProducto(Producto producto);
    Producto updateProducto(int id, Producto producto);
    void deleteProducto(int id);

}
