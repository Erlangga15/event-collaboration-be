package com.eventhub.dti.common.exceptions;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String msg) {
        super(msg);
    }
}
