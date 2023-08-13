package com.example.tomato.external.controller;

import com.example.tomato.external.dto.request.refrigerator.RefrigeratorCreationRequest;
import com.example.tomato.external.dto.request.refrigerator.RefrigeratorUpdateRequest;
import com.example.tomato.external.entity.CommonRefrigerator;
import com.example.tomato.external.service.RefrigeratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(RefrigeratorController.class)
public class CommonRefrigeratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefrigeratorService refrigeratorService;

    @InjectMocks
    private RefrigeratorController refrigeratorController;

    private List<CommonRefrigerator> commonRefrigeratorList;

    @BeforeEach
    public void setup() {
        commonRefrigeratorList = new ArrayList<>();
        commonRefrigeratorList.add(new CommonRefrigerator(1L, "Item1", 2L, LocalDateTime.now()));
        commonRefrigeratorList.add(new CommonRefrigerator(2L, "Item2", 3L, LocalDateTime.now()));
    }

    @Test
    public void testCreate() throws Exception {
        RefrigeratorCreationRequest request = new RefrigeratorCreationRequest("NewItem", 2L, LocalDateTime.now());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/refrigerator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"NewItem\",\"quantity\":2,\"expiry\":\"2023-01-01T12:34:56\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(refrigeratorService, times(1)).create(any());
    }

    @Test
    public void testRead() throws Exception {
        when(refrigeratorService.read(anyBoolean(), anyLong())).thenReturn(commonRefrigeratorList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/refrigerator")
                        .param("expired", "false")
                        .param("expiredBeforeDays", "3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));

        verify(refrigeratorService, times(1)).read(anyBoolean(), anyLong());
    }

    @Test
    public void testUpdate() throws Exception {
        RefrigeratorUpdateRequest request = new RefrigeratorUpdateRequest(1L, "UpdatedItem", 4L, LocalDateTime.now());

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/refrigerator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"UpdatedItem\",\"quantity\":4,\"expiry\":\"2023-01-01T01:23:45\"}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(refrigeratorService, times(1)).update(any());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/refrigerator")
                        .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(refrigeratorService, times(1)).delete(1L);
    }

    @Test
    public void testDeleteAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/refrigerator/all"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(refrigeratorService, times(1)).deleteAll();
    }

    @Test
    public void testDeleteOos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/refrigerator/oos"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(refrigeratorService, times(1)).deleteOos();
    }

    @Test
    public void testDeleteExpired() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/refrigerator/expired"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(refrigeratorService, times(1)).deleteExpired();
    }
}
