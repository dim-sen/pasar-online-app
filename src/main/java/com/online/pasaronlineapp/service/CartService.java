package com.online.pasaronlineapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    ResponseEntity<Object> addToCart(CartDto cartDto, Long userId, Long itemId);

    ResponseEntity<Object> getCartById(Long userId, Long itemId);

    ResponseEntity<Object> getAllCarts();

    ResponseEntity<Object> updateCartById(Long userId, Long itemId, CartDto cartDto);

    ResponseEntity<Object> deleteCartById(Long userId, Long itemId);
}
