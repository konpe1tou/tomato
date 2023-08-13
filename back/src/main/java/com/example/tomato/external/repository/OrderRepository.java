package com.example.tomato.external.repository;

import com.example.tomato.external.entity.CommonOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CommonOrder, Long> {
}
