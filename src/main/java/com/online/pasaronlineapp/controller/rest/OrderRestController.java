package com.online.pasaronlineapp.controller.rest;

import com.online.pasaronlineapp.domain.dto.OrderDto;
import com.online.pasaronlineapp.service.impl.rest.OrderRestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/order")
public class OrderRestController {

    @Autowired
    private OrderRestServiceImpl orderRestService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createOrder(@RequestBody OrderDto orderDto) {
        return orderRestService.createOrder(orderDto);
    }
}
