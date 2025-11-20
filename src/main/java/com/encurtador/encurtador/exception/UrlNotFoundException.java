package com.encurtador.encurtador.exception;

public class UrlNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UrlNotFoundException(String msg) {
        super(msg);
    }

    public UrlNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}