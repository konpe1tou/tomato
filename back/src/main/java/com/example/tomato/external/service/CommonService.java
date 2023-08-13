package com.example.tomato.external.service;

import com.example.tomato.internal.service.UniqueIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    @Autowired
    protected UniqueIdService uniqueIdService;
}
