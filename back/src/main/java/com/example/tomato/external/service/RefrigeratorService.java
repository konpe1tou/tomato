package com.example.tomato.external.service;

import com.example.tomato.core.constants.UniqueIdType;
import com.example.tomato.core.error.Error;
import com.example.tomato.core.exception.NotFoundIdException;
import com.example.tomato.external.dto.request.refrigerator.RefrigeratorCreationRequest;
import com.example.tomato.external.dto.request.refrigerator.RefrigeratorUpdateRequest;
import com.example.tomato.external.entity.CommonRefrigerator;
import com.example.tomato.external.repository.RefrigeratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RefrigeratorService extends CommonService {

    @Autowired
    private RefrigeratorRepository refrigeratorRepository;

    @Transactional
    public void create(RefrigeratorCreationRequest request) {
        CommonRefrigerator commonRefrigerator = new CommonRefrigerator(request, uniqueIdService.incrementUniqueId(UniqueIdType.REFRIGERATOR));
        refrigeratorRepository.save(commonRefrigerator);
    }

    @Transactional
    public List<CommonRefrigerator> read(boolean expired, Long expiredBeforeDays) {
        if (expired) {
            return refrigeratorRepository.findByExpiryBefore(LocalDateTime.now());
        }
        if (Objects.nonNull(expiredBeforeDays)) {
            LocalDateTime today = LocalDateTime.now()
                    .withHour(0)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);
            return refrigeratorRepository.findByExpiryBetween(today, today.plusDays(expiredBeforeDays + 1L).minusNanos(1L));
        }
        return refrigeratorRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    public void update(RefrigeratorUpdateRequest request) {
        Optional<CommonRefrigerator> refrigerator = refrigeratorRepository.findById(request.getId());
        if (refrigerator.isEmpty()) {
            throw new NotFoundIdException(Error.A0001);
        }
        CommonRefrigerator updatedCommonRefrigerator = new CommonRefrigerator(request);
        refrigeratorRepository.save(updatedCommonRefrigerator);
    }

    @Transactional
    public void delete(Long id) {
        Optional<CommonRefrigerator> refrigerator = refrigeratorRepository.findById(id);
        if(refrigerator.isEmpty()) {
            throw new NotFoundIdException(Error.A0001);
        }
        refrigeratorRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        refrigeratorRepository.deleteAll();
    }

    @Transactional
    public void deleteOos() {
        List<CommonRefrigerator> commonRefrigeratorList = refrigeratorRepository.findByQuantityLessThanEqual(0L);
        commonRefrigeratorList.forEach(refrigerator -> refrigeratorRepository.delete(refrigerator));
    }

    @Transactional
    public void deleteExpired() {
        List<CommonRefrigerator> commonRefrigeratorList = refrigeratorRepository.findByExpiryBefore(LocalDateTime.now());
        commonRefrigeratorList.forEach(refrigerator -> refrigeratorRepository.delete(refrigerator));
    }
}
