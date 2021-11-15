package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(long id) {
        super(ConstantUtil.PRODUCT_NOT_FOUND + id);
    }

}
