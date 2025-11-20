package com.encurtador.encurtador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_engajamento")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EngagementModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "click_count", nullable = false)
    private int clickCount = 0;

    @Column(name = "clicked_at", nullable = false)
    private LocalDateTime clickedAt;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "first_clicked_at", nullable = false)
    private LocalDateTime firstClickedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id", nullable = false)
    private UrlModel url;
}
