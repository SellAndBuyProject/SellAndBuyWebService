package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Producto;
import com.sanvalero.SellAndBuy.exception.ProductoNotFoundException;
import com.sanvalero.SellAndBuy.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Servicio que busca todos los productos.
     * @return Set de objetos de tipo Producto.
     */
    @Override
    public Set<Producto> findAll() {
        return productoRepository.findAll();
    }

    /**
     * Servicio que busca un producto identificado por su id.
     * @param id id del producto que se quiere buscar.
     * @return objeto de tipo Producto.
     */
    @Override
    public Producto findById(int id) {
        return productoRepository.findById(id).
                orElseThrow(() -> new ProductoNotFoundException(id));
    }

    /**
     * Servicio que guarda un nuevo producto.
     * @param producto objeto de tipo Producto que se quiere guardar.
     * @return objeto de tipo Producto guardado.
     */
    @Override
    public Producto addProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Servicio que edita un producto guardado identificado por su id.
     * @param id id del producto que se quiere editar.
     * @param producto objeto de tipo Producto editado.
     * @return objet de tipo Producto actualilzado.
     */
    @Override
    public Producto updateProducto(int id, Producto producto) {
        Producto productoGuardado = productoRepository.findById(id).
                orElseThrow(() -> new ProductoNotFoundException(id));
        productoRepository.delete(productoGuardado);
        return productoRepository.save(producto);
    }

    /**
     * Servicio que elimina un producto guardado identificado por su id.
     * @param id id del productoq ue se quiere eliminar.
     */
    @Override
    public void deleteProducto(int id) {
        Producto productoGuardado = productoRepository.findById(id).
                orElseThrow(() -> new ProductoNotFoundException(id));
        productoRepository.deleteById(id);
    }
}
