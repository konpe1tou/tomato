package com.example.tomato.external.service;

import com.example.tomato.core.constants.UniqueIdType;
import com.example.tomato.external.dto.request.order.OrderRequest;
import com.example.tomato.external.entity.CommonOrder;
import com.example.tomato.external.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService extends CommonService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void create(OrderRequest request) {
        CommonOrder commonOrder = new CommonOrder(request, uniqueIdService.incrementUniqueId(UniqueIdType.ORDER), LocalDateTime.now());
        orderRepository.save(commonOrder);
    }

    @Transactional
    public List<CommonOrder> read() {
        return orderRepository.findAll();
    }
}
