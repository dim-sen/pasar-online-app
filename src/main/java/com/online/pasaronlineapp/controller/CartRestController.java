package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dto.CartDto;
import com.online.pasaronlineapp.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartRestController {

    @Autowired
    private CartServiceImpl cartService;

    @PostMapping(value = "")
    public ResponseEntity<Object> addToCart(CartDto cartDto, Long userId, Long itemId) {
        return cartService.addToCart(cartDto, userId, itemId);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllCarts() {
        return cartService.getAllCarts();
    }
}
