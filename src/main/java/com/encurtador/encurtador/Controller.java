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
    public ResponseEntity<UrlDto> shortenUrl(@RequestBody UrlForm urlForm) {
        UrlDto endereco = urlService.createShortUrl(urlForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }


    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> getUrl(@PathVariable String shortUrl) {
        String enderco = urlService.rediretctLongUrl(shortUrl, httpServletRequest);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(enderco))
                .build();
    }

    @GetMapping("/about/{url}")
    public ResponseEntity<EngagementUrlDto> getEngagementUrl(@PathVariable String url) {
        EngagementUrlDto engagementUrlDto = urlService.getUrlEngagement(url);
        return ResponseEntity.status(200).body(engagementUrlDto);
    }

}
