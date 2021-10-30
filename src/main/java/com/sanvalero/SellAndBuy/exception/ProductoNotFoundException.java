package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

public class ProductoNotFoundException extends RuntimeException{

    public ProductoNotFoundException() {
        super();
    }

    public ProductoNotFoundException(String message) {
        super(message);
    }

    public ProductoNotFoundException(int id) {
        super(ConstantUtil.PRODUCTO_NOT_FOUND + id);
    }

}
