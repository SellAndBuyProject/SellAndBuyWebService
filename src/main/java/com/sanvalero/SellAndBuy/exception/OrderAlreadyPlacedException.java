package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public class OrderAlreadyPlacedException extends RuntimeException {

    public OrderAlreadyPlacedException() {
        super();
    }

    public OrderAlreadyPlacedException(String message) {
        super(message);
    }

    public OrderAlreadyPlacedException(long id) {
        super(ConstantUtil.ORDER_ALREADY_PLACED_EXCEPTION + id);
    }
}
