package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public class UserDuplicateException extends RuntimeException  {

    public UserDuplicateException() {
        super(ConstantUtil.USER_DUPLICATE_EXCEPTION);
    }
}
