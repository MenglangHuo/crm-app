package com.bronx.crm.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Exception thrown for bad request errors.
 * Results in HTTP 400 Bad Request response.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {


    public BadRequestException(String message) {
        super("BAD_REQUEST_REQUEST", message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
