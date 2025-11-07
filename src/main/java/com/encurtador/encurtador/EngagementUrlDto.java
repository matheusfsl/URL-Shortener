package com.encurtador.encurtador;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EngagementUrlDto {
    private String url;
    private Integer clickCount=0;
    private Integer uniqueIps = 0;
    private Double clicksPerHour = 0.0;
    private Double avgTimeToAccessSeconds;
    private LocalDateTime createdAt;
}
