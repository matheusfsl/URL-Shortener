package com.encurtador.encurtador;

public class UrlShorteningException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UrlShorteningException(String msg) {
        super(msg);
    }

    public UrlShorteningException(String msg, Throwable cause) {
        super(msg, cause);
    }
}