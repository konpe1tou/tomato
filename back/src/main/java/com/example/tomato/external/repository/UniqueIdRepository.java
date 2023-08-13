package com.example.tomato.external.repository;

import com.example.tomato.core.constants.UniqueIdType;
import com.example.tomato.external.entity.UniqueId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface UniqueIdRepository extends JpaRepository<UniqueId, UniqueIdType> {
    @Lock(LockModeType.READ)
    Optional<UniqueId> findById(UniqueIdType uniqueIdType);
}
