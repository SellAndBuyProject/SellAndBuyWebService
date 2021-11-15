package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

public class OrderNotSuccess extends RuntimeException{

    public OrderNotSuccess() {
        super(ConstantUtil.ORDER_NOT_SUCCESS);
    }

    public OrderNotSuccess(String message) {
        super(message);
    }

    public OrderNotSuccess(long id) {
        super("" + id);
    }

}
