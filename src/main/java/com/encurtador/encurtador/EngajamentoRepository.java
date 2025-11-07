package com.encurtador.encurtador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EngajamentoRepository extends JpaRepository<EngajamentoModel, Long> {

    Optional<EngajamentoModel> findByUrlAndIp(UrlModel url, String ip);
    List<EngajamentoModel> findAllByUrlShortCode(String shortCode);

}
