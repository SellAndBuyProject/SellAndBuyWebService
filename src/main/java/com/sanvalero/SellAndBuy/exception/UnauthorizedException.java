package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super(ConstantUtil.UNAUTHORIZED_EXCEPTION);
    }
}
