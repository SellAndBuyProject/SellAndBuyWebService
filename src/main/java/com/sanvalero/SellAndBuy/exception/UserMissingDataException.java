package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
public class UserMissingDataException extends RuntimeException  {

    public UserMissingDataException() {
        super(ConstantUtil.USER_MISSING_DATA_EXCEPTION);
    }
}
