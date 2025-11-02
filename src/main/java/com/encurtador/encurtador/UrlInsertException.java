package com.encurtador.encurtador;

public class UrlInsertException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UrlInsertException(String msg) {
        super(msg);
    }

    public UrlInsertException(String msg, Throwable cause) {
        super(msg, cause);
    }}
