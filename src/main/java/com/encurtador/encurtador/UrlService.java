package com.encurtador.encurtador;


import org.springframework.stereotype.Service;

import java.security.SecureRandom;


@Service
public class UrlService {


    private UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
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

    public String getUrlLong(String shortUrl) {
        UrlModel urlModel = urlRepository.findByShortCode(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException(
                        String.format("Url não encontrada")
                ));

        return urlModel.getLongUrl();
    }

}
