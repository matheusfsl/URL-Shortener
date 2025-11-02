package com.encurtador.encurtador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlModel, Long> {
    boolean existsByShortCode(String shortCode);

    Optional<UrlModel> findByShortCode(String shortCode);

}
