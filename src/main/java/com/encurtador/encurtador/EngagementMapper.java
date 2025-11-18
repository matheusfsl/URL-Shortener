package com.encurtador.encurtador;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EngagementMapper {

    public EngagementModel createNew(UrlModel url, String ip) {
        EngagementModel engagement = new EngagementModel();
        engagement.setUrl(url);
        engagement.setIp(ip);
        engagement.setClickCount(1);
        engagement.setFirstClickedAt(LocalDateTime.now());
        engagement.setClickedAt(LocalDateTime.now());
        return engagement;
    }

    public void updateExisting(EngagementModel engagement) {
        engagement.setClickCount(engagement.getClickCount() + 1);
        engagement.setClickedAt(LocalDateTime.now());
    }
}

