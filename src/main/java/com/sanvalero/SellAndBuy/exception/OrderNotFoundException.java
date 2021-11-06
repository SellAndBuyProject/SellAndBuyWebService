package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String mensaje) {
        super();
    }

    public OrderNotFoundException(long userId) {
        super(ConstantUtil.PEDIDO_BY_USER_NOT_FOUND + " " + userId);
    }

}
