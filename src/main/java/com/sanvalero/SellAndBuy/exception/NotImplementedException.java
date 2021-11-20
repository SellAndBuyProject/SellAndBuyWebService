package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public class NotImplementedException extends RuntimeException {

    public NotImplementedException() {
        super(ConstantUtil.NOT_IMPLEMENTED_EXCEPTION);
    }
}
