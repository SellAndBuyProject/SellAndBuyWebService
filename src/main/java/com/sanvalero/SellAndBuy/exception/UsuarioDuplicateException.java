package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public class UsuarioDuplicateException extends RuntimeException  {

    public UsuarioDuplicateException() {
        super();
    }

    public UsuarioDuplicateException(String email) {
        super(ConstantUtil.USUARIO_DUPLICATE_EXCEPTION + email);
    }
}
