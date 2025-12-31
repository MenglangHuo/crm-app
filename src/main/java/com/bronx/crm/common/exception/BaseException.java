package com.bronx.crm.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final String code;

    public BaseException(String message) {
        super(message);
        this.code = "GENERAL_ERROR";
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.code = "GENERAL_ERROR";
    }
}
