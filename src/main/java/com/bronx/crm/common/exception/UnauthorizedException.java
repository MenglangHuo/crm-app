package com.bronx.crm.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Exception thrown when user is not authorized.
 * Results in HTTP 401 Unauthorized response.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends BaseException {

    public UnauthorizedException(String message) {
        super("UNAUTHORIZED", message);
    }

    public UnauthorizedException() {
        super("UNAUTHORIZED", "You are not authorized to perform this action");
    }
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
