package com.encurtador.encurtador;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

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
        urlDto.setOrinalUrl(urlModel.getLongUrl());
        urlDto.setUrlShortner(domain+urlModel.getShortCode());
        return urlDto;
    }

    public EngagementUrlDto modelListToEngajamentoDto(List<EngajamentoModel> engajamentos) {
        if (engajamentos == null || engajamentos.isEmpty()) {
            return null;
        }

        UrlModel url = engajamentos.get(0).getUrl();

        int totalClicks = engajamentos.stream()
                .mapToInt(EngajamentoModel::getClickCount)
                .sum();

        long uniqueIps = engajamentos.stream()
                .map(EngajamentoModel::getIp)
                .distinct()
                .count();

        long hoursSinceCreation = java.time.Duration.between(
                url.getCreatedAt(),
                LocalDateTime.now()
        ).toHours();

        double clicksPerHour = hoursSinceCreation > 0
                ? (double) totalClicks / hoursSinceCreation
                : totalClicks;

        LocalDateTime firstClick = engajamentos.stream()
                .map(EngajamentoModel::getClickedAt)
                .min(LocalDateTime::compareTo)
                .orElse(url.getCreatedAt());

        double timeToFirstClick = Duration.between(url.getCreatedAt(), firstClick).toSeconds();

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
