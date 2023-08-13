package com.example.tomato.internal.service;

import com.example.tomato.core.constants.UniqueIdType;
import com.example.tomato.external.entity.UniqueId;
import com.example.tomato.external.repository.UniqueIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UniqueIdService {

    @Autowired
    private UniqueIdRepository uniqueIdRepository;

    public Long incrementUniqueId(UniqueIdType uniqueIdType) {
        // 呼び出し元Serviceでトランザクションを張る
        Optional<UniqueId> currentUniqueId = uniqueIdRepository.findById(uniqueIdType);
        UniqueId nextUniqueId = new UniqueId();
        if (currentUniqueId.isPresent()) {
            nextUniqueId = currentUniqueId.get();
            nextUniqueId.setLatestCount(currentUniqueId.get().getLatestCount() + 1);
        } else {
            nextUniqueId.setUniqueIdType(uniqueIdType);
            nextUniqueId.setLatestCount(uniqueIdType.getInitialCount());
        }
        uniqueIdRepository.save(nextUniqueId);
        return nextUniqueId.getLatestCount();
    }
}
