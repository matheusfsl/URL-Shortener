package com.encurtador.encurtador;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class UrlMapper {
    private static final String domain= "http://localhost:8080/";

    public UrlModel formTomodel(UrlForm urlForm){
        UrlModel urlModel = new UrlModel();
        urlModel.setLongUrl(urlForm.getLongUrl());
        return urlModel;
    }
    public UrlDto modelToDto (UrlModel urlModel){
        UrlDto urlDto = new UrlDto();
        urlDto.setOriginalUrl(urlModel.getLongUrl());
        urlDto.setUrlShortner(domain+urlModel.getShortCode());
        return urlDto;
    }


    public EngagementUrlDto modelListToEngajamentoDto(List<EngagementModel> engajamentos, UrlModel url) {

        int totalClicks = 0;
        long uniqueIps = 0;
        double clicksPerHour = 0;
        double timeToFirstClick = 0;

        if (engajamentos != null && !engajamentos.isEmpty()) {

            totalClicks = engajamentos.stream()
                    .mapToInt(EngagementModel::getClickCount)
                    .sum();

            uniqueIps = engajamentos.stream()
                    .map(EngagementModel::getIp)
                    .distinct()
                    .count();

            long hoursSinceCreation = Duration.between(
                    url.getCreatedAt(),
                    LocalDateTime.now()
            ).toHours();

            clicksPerHour = hoursSinceCreation > 0
                    ? (double) totalClicks / hoursSinceCreation
                    : totalClicks;

            LocalDateTime firstClick = engajamentos.stream()
                    .map(EngagementModel::getFirstClickedAt)
                    .filter(Objects::nonNull)
                    .min(LocalDateTime::compareTo)
                    .orElse(url.getCreatedAt());

            timeToFirstClick = Duration.between(url.getCreatedAt(), firstClick).toSeconds();
        }

        EngagementUrlDto dto = new EngagementUrlDto();
        dto.setUrl(domain + url.getShortCode());
        dto.setClickCount(totalClicks);
        dto.setUniqueIps((int) uniqueIps);
        dto.setClicksPerHour(clicksPerHour);
        dto.setAvgTimeToAccessSeconds(timeToFirstClick);
        dto.setCreatedAt(url.getCreatedAt());

        return dto;
    }

}
