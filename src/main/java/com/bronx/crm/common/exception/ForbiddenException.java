package com.bronx.crm.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Exception thrown when access is forbidden.
 * Results in HTTP 403 Forbidden response.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends BaseException {
    public ForbiddenException(String message) {
        super("FORBIDDEN", message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
