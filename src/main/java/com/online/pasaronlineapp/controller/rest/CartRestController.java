package com.online.pasaronlineapp.controller.rest;

import com.online.pasaronlineapp.domain.dto.CartDto;
import com.online.pasaronlineapp.service.impl.rest.CartRestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartRestController {

    @Autowired
    private CartRestServiceImpl cartRestService;

    @PostMapping(value = "")
    public ResponseEntity<Object> addToCart(@RequestBody CartDto cartDto) {
        return cartRestService.addToCart(cartDto);
    }
}
