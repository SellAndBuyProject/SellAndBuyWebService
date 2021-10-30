package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public class UsuarioMissingDataException extends RuntimeException  {

    public UsuarioMissingDataException() {
        super(ConstantUtil.USUARIO_MISSING_DATA_EXCEPTION);
    }
}
