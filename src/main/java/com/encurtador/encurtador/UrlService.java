package com.encurtador.encurtador;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;


@Service
public class UrlService {


    private UrlRepository urlRepository;
    private EngajamentoRepository engajamentoRepository;

    public UrlService(UrlRepository urlRepository, EngajamentoRepository engajamentoRepository) {
        this.urlRepository = urlRepository;
        this.engajamentoRepository = engajamentoRepository;
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

    public String createShortUrl(UrlForm urlForm) {
        UrlModel urlModel = new UrlModel();
        urlModel.setLongUrl(urlForm.getLongUrl());
        do {
            urlModel.setShortCode(generateCode());
        } while (urlRepository.existsByShortCode(urlModel.getShortCode()));
        urlRepository.save(urlModel);
        return urlModel.getShortCode();
    }

    public String getUrlLong(String shortUrl, HttpServletRequest httpServletRequest) {
        UrlModel urlModel = urlRepository.findByShortCode(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException(
                        String.format("Url '%s' not found", shortUrl)
                ));
//        String ip = httpServletRequest.getRemoteAddr();

        // Permite IP customizado para testes via header
        String ip = httpServletRequest.getHeader("X-Test-IP");
        if (ip == null || ip.isEmpty()) {
            ip = httpServletRequest.getRemoteAddr();
        }
        EngajamentoModel engajamentoModel = engajamentoRepository.findByUrlAndIp(urlModel,ip)
                .orElse(new EngajamentoModel());

        engajamentoModel.setUrl(urlModel);
        engajamentoModel.setIp(ip);
        engajamentoModel.setClickCount(engajamentoModel.getClickCount() + 1);
        engajamentoRepository.save(engajamentoModel);

        urlRepository.save(urlModel);

        return urlModel.getLongUrl();
    }

}
