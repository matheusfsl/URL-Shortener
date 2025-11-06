package com.encurtador.encurtador;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "url_engajamento")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EngajamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "click_count", nullable = true)
    private int clickCount;

    @Column(name = "ip", nullable = false)
    private String ip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id", nullable = false)
    private UrlModel url;
}
