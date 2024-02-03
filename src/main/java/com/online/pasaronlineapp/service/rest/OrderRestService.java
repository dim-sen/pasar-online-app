package com.online.pasaronlineapp.service.rest;

import com.online.pasaronlineapp.domain.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderRestService {

    ResponseEntity<Object> createOrder(OrderDto orderDto);
}
