package com.example.tomato.external.repository;

import com.example.tomato.external.entity.CommonRefrigerator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RefrigeratorRepository extends JpaRepository<CommonRefrigerator, Long> {
    List<CommonRefrigerator> findAllByOrderByIdAsc();

    List<CommonRefrigerator> findByExpiryBefore(LocalDateTime currentDateTime);

    List<CommonRefrigerator> findByExpiryBetween(LocalDateTime currentDateTime, LocalDateTime untilDatetime);

    List<CommonRefrigerator> findByQuantityLessThanEqual(Long quantity);
}
