package com.encurtador.encurtador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EngajamentoRepository extends JpaRepository<EngajamentoModel, Long> {

    Optional<EngajamentoModel> findByUrlAndIp(UrlModel url, String ip);

    @Query("SELECT e FROM EngajamentoModel e JOIN FETCH e.url WHERE e.url.shortCode = :shortCode")
    List<EngajamentoModel> findAllByUrlShortCode(String shortCode);

}
