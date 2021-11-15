package com.sanvalero.SellAndBuy.exception;

import com.sanvalero.SellAndBuy.util.ConstantUtil;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super(ConstantUtil.USER_NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(long id) {
        super(ConstantUtil.USER_NOT_FOUND + id);
    }
}
