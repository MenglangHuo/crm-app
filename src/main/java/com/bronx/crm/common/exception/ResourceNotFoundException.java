package com.bronx.crm.common.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Exception thrown when a requested resource is not found.
 * Results in HTTP 404 Not Found response.
 */


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resource, String field, Object value) {
        super("NOT_FOUND", String.format("%s not found with %s: %s", resource, field, value));
    }

    public ResourceNotFoundException(String message) {
        super("NOT_FOUND", message);
    }
}