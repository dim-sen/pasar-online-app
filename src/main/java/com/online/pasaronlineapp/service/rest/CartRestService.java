package com.online.pasaronlineapp.service.rest;

import com.online.pasaronlineapp.domain.dto.CartDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CartRestService {

    ResponseEntity<Object> addToCart(CartDto cartDto);

    ResponseEntity<Object> getCartById(Long id);

    ResponseEntity<Object> getAllCarts();

    ResponseEntity<Object> updateCartById(Long id);

    ResponseEntity<Object> deleteCartById(Long id);
}
