package com.example.tomato.external.service;

import com.example.tomato.core.exception.NotFoundIdException;
import com.example.tomato.external.dto.request.refrigerator.RefrigeratorCreationRequest;
import com.example.tomato.external.dto.request.refrigerator.RefrigeratorUpdateRequest;
import com.example.tomato.external.entity.CommonRefrigerator;
import com.example.tomato.external.repository.RefrigeratorRepository;
import com.example.tomato.internal.service.UniqueIdService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommonRefrigeratorServiceTest extends RefrigeratorService {

    @Mock
    private RefrigeratorRepository refrigeratorRepository;

    @Mock
    private UniqueIdService uniqueIdService;

    @InjectMocks
    private RefrigeratorService refrigeratorService;


    @Test
    public void testCreate() {
        RefrigeratorCreationRequest request = new RefrigeratorCreationRequest("Item", 2L, LocalDateTime.now());
        CommonRefrigerator commonRefrigerator = new CommonRefrigerator(request, 0L);

        when(refrigeratorRepository.save(any(CommonRefrigerator.class))).thenReturn(commonRefrigerator);

        refrigeratorService.create(request);

        verify(refrigeratorRepository, times(1)).save(commonRefrigerator);
    }

    @Test
    public void testReadExpired() {
        List<CommonRefrigerator> expectedList = new ArrayList<>();
        when(refrigeratorRepository.findByExpiryBefore(any(LocalDateTime.class))).thenReturn(expectedList);

        List<CommonRefrigerator> result = refrigeratorService.read(true, null);

        assertThat(result).isEqualTo(expectedList);
    }

    @Test
    public void testReadExpiredBeforeDays() {
        List<CommonRefrigerator> expectedList = new ArrayList<>();
        when(refrigeratorRepository.findByExpiryBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(expectedList);

        List<CommonRefrigerator> result = refrigeratorService.read(false, 7L);

        assertThat(result).isEqualTo(expectedList);
    }

    @Test
    public void testReadDefault() {
        List<CommonRefrigerator> expectedList = new ArrayList<>();
        when(refrigeratorRepository.findAllByOrderByIdAsc()).thenReturn(expectedList);

        List<CommonRefrigerator> result = refrigeratorService.read(false, null);

        assertThat(result).isEqualTo(expectedList);
    }

    @Test
    public void testUpdate() {
        Long id = 0L;
        RefrigeratorUpdateRequest request = new RefrigeratorUpdateRequest(id, "NewItem", 2L, LocalDateTime.now());
        CommonRefrigerator commonRefrigerator = new CommonRefrigerator(request);

        when(refrigeratorRepository.findById(anyLong())).thenReturn(Optional.of(commonRefrigerator));
        when(refrigeratorRepository.save(commonRefrigerator)).thenReturn(commonRefrigerator);

        refrigeratorService.update(request);

        verify(refrigeratorRepository, times(1)).save(commonRefrigerator);
    }

    @Test
    public void testUpdateNotFound() {
        Long id = 1L;
        RefrigeratorUpdateRequest request = new RefrigeratorUpdateRequest(id, "NewItem", 2L, LocalDateTime.now());

        when(refrigeratorRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> refrigeratorService.update(request))
                .isInstanceOf(NotFoundIdException.class);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        CommonRefrigerator commonRefrigerator = new CommonRefrigerator(id, "Item", 2L, LocalDateTime.now());

        when(refrigeratorRepository.findById(id)).thenReturn(Optional.of(commonRefrigerator));

        refrigeratorService.delete(id);

        verify(refrigeratorRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteNotFound() {
        Long id = 1L;

        when(refrigeratorRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> refrigeratorService.delete(id))
                .isInstanceOf(NotFoundIdException.class);
    }

    @Test
    public void testDeleteAll() {
        refrigeratorService.deleteAll();

        verify(refrigeratorRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteOos() {
        refrigeratorService.deleteOos();

        verify(refrigeratorRepository, times(1)).findByQuantityLessThanEqual(0L);
    }

    @Test
    public void testDeleteExpired() {
        refrigeratorService.deleteExpired();

        verify(refrigeratorRepository, times(1)).findByQuantityLessThanEqual(0L);
    }
}
