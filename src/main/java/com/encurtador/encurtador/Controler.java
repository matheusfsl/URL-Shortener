package com.encurtador.encurtador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping
public class Controler {
    private final UrlService urlService;

    public Controler(UrlService urlService) {
        this.urlService = urlService;
    }



}
