package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

public class PedidoNotSuccess extends RuntimeException{

    public PedidoNotSuccess() {
        super(ConstantUtil.PEDIDO_NOT_SUCCESS);
    }

    public PedidoNotSuccess(String mensaje) {
        super(mensaje);
    }

    public PedidoNotSuccess(int id) {
        super("" + id);
    }

}
