package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

public class OrderNotSuccess extends RuntimeException{

    public OrderNotSuccess() {
        super(ConstantUtil.PEDIDO_NOT_SUCCESS);
    }

    public OrderNotSuccess(String mensaje) {
        super(mensaje);
    }

    public OrderNotSuccess(long id) {
        super("" + id);
    }

}
