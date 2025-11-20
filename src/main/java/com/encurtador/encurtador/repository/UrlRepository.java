package com.encurtador.encurtador.repository;

import com.encurtador.encurtador.model.UrlModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlModel, Long> {
    boolean existsByShortCode(String shortCode);

    Optional<UrlModel> findByShortCode(String shortCode);
    Optional<UrlModel> findByShortCodeAndIsActiveTrue(String shortCode);
    List<UrlModel> findAllByIsActiveTrue();

    @Modifying
    @Query("UPDATE UrlModel u SET u.isActive = false " +
            "WHERE u.isActive = true " +
            "AND u.createdAt < :date " +
            "AND (u.lastClickedAt IS NULL OR u.lastClickedAt < :date)")
    int deactivateUrlsWithoutRecentClicks(@Param("date") LocalDateTime date);
}
