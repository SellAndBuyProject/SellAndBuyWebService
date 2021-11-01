package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

public class PedidoNotFoundException extends RuntimeException{

    public PedidoNotFoundException() {
        super();
    }

    public PedidoNotFoundException(String mensaje) {
        super();
    }

    public PedidoNotFoundException(int idUsuario) {
        super(ConstantUtil.PEDIDO_BY_USER_NOT_FOUND + " " + idUsuario);
    }

}
