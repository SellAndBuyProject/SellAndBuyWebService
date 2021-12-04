package com.sanvalero.SellAndBuy.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Response {

    public static final int NO_ERROR = 0;
    public static final int NOT_FOUND = 101;
    public static final int FORBIDDEN = 102;
    public static final int BAD_REQUEST = 103;
    public static final int NOT_ACCEPTABLE = 104;
    public static final int NOT_IMPLEMENTED = 105;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int USER_DUPLICATE = 201;
    public static final int PRODUCT_DUPLICATE = 202;
    public static final int PRODUCT_SOLD = 203;
    public static final int ALREADY_PLACED = 204;


    public static final String NO_MESSAGE = "";

    private Error error;

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class Error {
        private long errorCode;
        private String message;
    }

    public static Response noErrorResponse() {
        return new Response(new Error(NO_ERROR, NO_MESSAGE));
    }

    public static Response errorResponse(int errorCode, String errorMessage) {
        return new Response(new Error(errorCode, errorMessage));
    }
}
