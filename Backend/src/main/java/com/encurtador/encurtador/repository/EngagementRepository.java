package com.encurtador.encurtador.repository;

import com.encurtador.encurtador.model.EngagementModel;
import com.encurtador.encurtador.model.UrlModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EngagementRepository extends JpaRepository<EngagementModel, Long> {

    Optional<EngagementModel> findByUrlAndIp(UrlModel url, String ip);

    @Query("SELECT e FROM EngagementModel e JOIN FETCH e.url WHERE e.url.shortCode = :shortCode")
    List<EngagementModel> findAllByUrlShortCode(String shortCode);

}
