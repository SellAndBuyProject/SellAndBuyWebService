package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public class ProductDuplicateException extends RuntimeException  {

    public ProductDuplicateException() {
        super(ConstantUtil.PRODUCT_DUPLICATE_EXCEPTION);
    }

    public ProductDuplicateException(long id) {
        super(ConstantUtil.PRODUCT_DUPLICATE_EXCEPTION);
    }
}
