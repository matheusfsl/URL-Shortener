package com.encurtador.encurtador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Controller {
    private final UrlService urlService;

    public Controller(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> shortenUrl (@RequestBody UrlForm urlForm) {
        String endereco = urlService.createShortUrl(urlForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }


}
