package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public class ProductSoldException extends RuntimeException {

    public ProductSoldException() {
        super();
    }

    public ProductSoldException(String message) {
        super(message);
    }

    public ProductSoldException(long id) {
        super(ConstantUtil.PRODUCT_SOLD_EXCEPTION + id);
    }
}
