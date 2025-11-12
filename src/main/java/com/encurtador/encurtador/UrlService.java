package com.encurtador.encurtador;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final EngajamentoRepository engajamentoRepository;
    private final UrlMapper urlMapper;


    public UrlService(UrlRepository urlRepository, EngajamentoRepository engajamentoRepository, UrlMapper urlMapper) {
        this.urlRepository = urlRepository;
        this.engajamentoRepository = engajamentoRepository;
        this.urlMapper = urlMapper;
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateCode() {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public UrlDto createShortUrl(UrlForm urlForm) {
        UrlModel newUrlModel = urlMapper.formTomodel(urlForm);
        newUrlModel.setLongUrl(urlForm.getLongUrl());
        do {
            newUrlModel.setShortCode(generateCode());
        } while (urlRepository.existsByShortCode(newUrlModel.getShortCode()));
        newUrlModel.setCreatedAt(LocalDateTime.now());
        urlRepository.save(newUrlModel);
        return urlMapper.modelToDto(newUrlModel);
    }

    public String redirectLongUrl(String shortUrl, HttpServletRequest httpServletRequest) {

        UrlModel urlModel = urlRepository.findByShortCode(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException(
                        String.format("Url '%s' not found", shortUrl)
                ));

        String ip = httpServletRequest.getHeader("X-Test-IP");
        if (ip == null || ip.isEmpty()) {
            ip = httpServletRequest.getRemoteAddr();
        }

        EngajamentoModel engajamentoModel = engajamentoRepository
                .findByUrlAndIp(urlModel, ip)
                .orElse(null);

        if (engajamentoModel == null) {
            engajamentoModel = new EngajamentoModel();
            engajamentoModel.setUrl(urlModel);
            engajamentoModel.setIp(ip);
            engajamentoModel.setClickCount(1);
            engajamentoModel.setFirstClickedAt(LocalDateTime.now());
            engajamentoModel.setClickedAt(LocalDateTime.now());
        } else {
            engajamentoModel.setClickCount(engajamentoModel.getClickCount() + 1);
            engajamentoModel.setClickedAt(LocalDateTime.now());
        }

        engajamentoRepository.save(engajamentoModel);

        return urlModel.getLongUrl();
    }

    public EngagementUrlDto getUrlEngagement(String shortCode) {
        List<EngajamentoModel> engajamentos = engajamentoRepository.findAllByUrlShortCode(shortCode);

        if (engajamentos.isEmpty()) {
            throw new UrlNotFoundException(String.format("Url '%s' has not yet been accessed", shortCode));
        }
        return urlMapper.modelListToEngajamentoDto(engajamentos);
    }
}
