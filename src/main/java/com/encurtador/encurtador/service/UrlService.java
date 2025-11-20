package com.encurtador.encurtador.service;


import com.encurtador.encurtador.exception.UrlNotFoundException;
import com.encurtador.encurtador.exception.UrlShorteningException;
import com.encurtador.encurtador.utils.mapper.EngagementMapper;
import com.encurtador.encurtador.utils.mapper.UrlMapper;
import com.encurtador.encurtador.model.EngagementModel;
import com.encurtador.encurtador.model.UrlModel;
import com.encurtador.encurtador.repository.EngagementRepository;
import com.encurtador.encurtador.repository.UrlRepository;
import com.encurtador.encurtador.rest.dto.EngagementUrlDto;
import com.encurtador.encurtador.rest.dto.UrlDto;
import com.encurtador.encurtador.rest.form.UrlForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final EngagementRepository engagementRepository;
    private final UrlMapper urlMapper;
    private final EngagementMapper engagementMapper;


    public UrlService(UrlRepository urlRepository, EngagementRepository engagementRepository, UrlMapper urlMapper, EngagementMapper engagementMapper) {
        this.urlRepository = urlRepository;
        this.engagementRepository = engagementRepository;
        this.urlMapper = urlMapper;
        this.engagementMapper = engagementMapper;
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

    @Transactional
    public String redirectLongUrl(String shortUrl, HttpServletRequest httpServletRequest) {

        UrlModel urlModel = urlRepository.findByShortCodeAndIsActiveTrue(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException(
                        String.format("Url '%s' not found or has been deactivated", shortUrl)
                ));
        if (!urlModel.isActive()) {
            throw new UrlNotFoundException(
                    String.format("Url '%s' has been deactivated due to inactivity", shortUrl)
            );
        }

        String ip = httpServletRequest.getHeader("X-Test-IP");
        if (ip == null || ip.isEmpty()) {
            ip = httpServletRequest.getRemoteAddr();
        }
        urlModel.setLastClickedAt(LocalDateTime.now());

        EngagementModel engagementModel = engagementRepository
                .findByUrlAndIp(urlModel, ip)
                .orElse(null);

        if (engagementModel == null) {
            engagementModel = engagementMapper.createNew(urlModel, ip);
        } else {
            engagementMapper.updateExisting(engagementModel);
        }
        try {
            engagementRepository.save(engagementModel);
            return urlModel.getLongUrl();
        }catch (DataIntegrityViolationException e) {
            throw new UrlShorteningException("Error saving the shortened URL, integrity violation occurred.");
        } catch (Exception e) {
            throw new UrlShorteningException("An unexpected error occurred while shortening the URL.");
        }
    }

    public EngagementUrlDto getUrlEngagement(String shortCode) {

        UrlModel url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("Url not found or deactivated"));

        List<EngagementModel> engagements =
                engagementRepository.findAllByUrlShortCode(shortCode);

        return urlMapper.modelListToEngajamentoDto(engagements, url);
    }



}
