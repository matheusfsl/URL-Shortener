package com.encurtador.encurtador;

public class UrlAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UrlAlreadyExistsException(String msg) {
        super(msg);
    }

    public UrlAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }}
