package com.bronx.crm.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Exception thrown when there's a conflict with existing resource.
 * Results in HTTP 409 Conflict response.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends BaseException {
    public ConflictException(String mesasge){
        super(mesasge);
    }

    public ConflictException(String resource, String field, Object value) {
        super("ALREADY_EXISTS", String.format("%s already exists with %s: %s", resource, field, value));
    }
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
