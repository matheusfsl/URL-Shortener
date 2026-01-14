package com.encurtador.encurtador.rest;

import com.encurtador.encurtador.rest.dto.EngagementUrlDto;
import com.encurtador.encurtador.rest.dto.UrlDto;
import com.encurtador.encurtador.rest.form.UrlForm;
import com.encurtador.encurtador.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping
public class UrlController {
    private final UrlService urlService;
    private HttpServletRequest httpServletRequest;

    public UrlController(UrlService urlService, HttpServletRequest httpServletRequest) {
        this.urlService = urlService;
        this.httpServletRequest = httpServletRequest;
    }

    @PostMapping("/create")
    public ResponseEntity<UrlDto> shortenUrl(@Valid @RequestBody UrlForm urlForm) {
        UrlDto urlDto = urlService.createShortUrl(urlForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlDto);
    }


    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> getUrl(@PathVariable String shortUrl) {
        String shortCode = urlService.redirectLongUrl(shortUrl, httpServletRequest);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(shortCode))
                .build();
    }

    @GetMapping("/about/{url}")
    public ResponseEntity<EngagementUrlDto> getEngagementUrl(@PathVariable String url) {
        EngagementUrlDto engagementUrlDto = urlService.getUrlEngagement(url);
        return ResponseEntity.status(200).body(engagementUrlDto);
    }

}
