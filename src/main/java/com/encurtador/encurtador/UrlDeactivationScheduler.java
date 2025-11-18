package com.encurtador.encurtador;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class UrlDeactivationScheduler {

    private final UrlRepository urlRepository;

    @Value("${url.inactivity.days:30}")
    private int inactivityDays;

    public UrlDeactivationScheduler(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deactivateInactiveUrls() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(inactivityDays);
        urlRepository.deactivateUrlsWithoutRecentClicks(cutoffDate);
    }
}
