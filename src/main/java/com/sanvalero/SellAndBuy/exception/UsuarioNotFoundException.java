package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException() {
        super(ConstantUtil.USUARIO_NOT_FOUND);
    }

    public UsuarioNotFoundException(String message) {
        super(message);
    }

    public UsuarioNotFoundException(int id) {
        super(ConstantUtil.USUARIO_NOT_FOUND + id);
    }
}
