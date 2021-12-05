package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException() {
        super(ConstantUtil.ORDER_BY_USER_NOT_FOUND);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(long userId) {
        super(ConstantUtil.ORDER_BY_USER_NOT_FOUND + " " + userId);
    }

}
