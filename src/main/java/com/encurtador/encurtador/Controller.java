package com.encurtador.encurtador;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping
public class Controller {
    private final UrlService urlService;
    private HttpServletRequest httpServletRequest;

    public Controller(UrlService urlService, HttpServletRequest httpServletRequest) {
        this.urlService = urlService;
        this.httpServletRequest = httpServletRequest;
    }

    @PostMapping("/create")
    public ResponseEntity<String> shortenUrl(@RequestBody UrlForm urlForm) {
        String endereco = urlService.createShortUrl(urlForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }


    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> getUrl(@PathVariable String shortUrl) {
        String enderco = urlService.getUrlLong(shortUrl, httpServletRequest);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(enderco))
                .build();
    }

}
