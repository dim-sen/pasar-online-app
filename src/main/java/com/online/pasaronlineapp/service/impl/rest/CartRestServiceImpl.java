package com.online.pasaronlineapp.service.impl.rest;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.CartDao;
import com.online.pasaronlineapp.domain.dto.CartDto;
import com.online.pasaronlineapp.repository.CartRepository;
import com.online.pasaronlineapp.service.rest.CartRestService;
import com.online.pasaronlineapp.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartRestServiceImpl implements CartRestService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public ResponseEntity<Object> addToCart(CartDto cartDto) {
        try {
            CartDao cartDao = CartDao.builder()
                    .totalPrice(cartDto.getTotalPrice())
                    .build();

            cartRepository.save(cartDao);

            CartDto dto = CartDto.builder()
                    .totalPrice(cartDao.getTotalPrice())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new Cart. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getCartById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getAllCarts() {
        return null;
    }

    @Override
    public ResponseEntity<Object> updateCartById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteCartById(Long id) {
        return null;
    }
}
