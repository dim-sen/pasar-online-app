package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.CartDao;
import com.online.pasaronlineapp.domain.dto.CartDto;
import com.online.pasaronlineapp.repository.CartRepository;
import com.online.pasaronlineapp.service.CartService;
import com.online.pasaronlineapp.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public ResponseEntity<Object> addToCart(CartDto cartDto, Long userId, Long itemId) {
        try {
            log.info("Creating a new cart");
            Optional<CartDao> optionalCartDao = cartRepository.findByUserIdAndItemId(userId, itemId);

            if (optionalCartDao.isEmpty()) {
                log.info("There are no cart that have user id: " + userId + " and item id: " + itemId);
                CartDao cartDao = CartDao.builder()
                        .quantity(cartDto.getQuantity())
                        .user(cartDto.getUser())
                        .items(cartDto.getItems())
                        .build();

                CartDto dto = CartDto.builder()
                        .userId(cartDao.getUserId())
                        .itemId(cartDao.getItemId())
                        .quantity(cartDao.getQuantity())
                        .build();

                return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
            }

            log.info("There's a cart with user id: " + userId + " and item id: " + itemId + ". So, increase the number of quantity");
            CartDao cartDao = CartDao.builder()
                    .quantity(optionalCartDao.get().getQuantity() + 1)
                    .build();

            CartDto dto = CartDto.builder()
                    .userId(cartDao.getUserId())
                    .itemId(cartDao.getItemId())
                    .quantity(cartDao.getQuantity())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in adding to cart. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getCartById(Long userId, Long itemId) {
        try {
            log.info("Getting a cart by id");
            Optional<CartDao> optionalCartDao = cartRepository.findByUserIdAndItemId(userId, itemId);

            if (optionalCartDao.isEmpty()) {
                log.info("Cart not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Cart found");
            CartDto cartDto = CartDto.builder()
                    .userId(optionalCartDao.get().getUserId())
                    .itemId(optionalCartDao.get().getItemId())
                    .quantity(optionalCartDao.get().getQuantity())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, cartDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting a cart by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<Object> getAllCarts() {
        try {
            log.info("Getting all carts");
            List<CartDao> cartDaoList = cartRepository.findAll();
            List<CartDto> cartDtoList = new ArrayList<>();

            for (CartDao cartDao : cartDaoList) {
                cartDtoList.add(CartDto.builder()
                                .userId(cartDao.getUserId())
                                .itemId(cartDao.getItemId())
                                .quantity(cartDao.getQuantity())
                        .build());
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, cartDtoList, HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occurred in getting all carts. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateCartById(Long userId, Long itemId, CartDto cartDto) {
        try {
            log.info("Updating a cart by id");
            Optional<CartDao> optionalCartDao = cartRepository.findByUserIdAndItemId(userId, itemId);

            if (optionalCartDao.isEmpty()) {
                log.info("Cart not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Cart found");
            CartDao cartDao = optionalCartDao.get();
            cartDao.setUserId(cartDao.getUserId());
            cartDao.setItemId(cartDao.getItemId());
            cartDao.setQuantity(cartDao.getQuantity());
            cartRepository.save(cartDao);

            CartDto dto = CartDto.builder()
                    .userId(cartDao.getUserId())
                    .itemId(cartDao.getItemId())
                    .quantity(cartDao.getQuantity())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in updating a cart by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<Object> deleteCartById(Long userId, Long itemId) {
        try {
            log.info("Deleting a cart by id");
            Optional<CartDao> optionalCartDao = cartRepository.findByUserIdAndItemId(userId, itemId);

            if (optionalCartDao.isEmpty()) {
                log.info("Cart not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Cart found");
            cartRepository.delete(optionalCartDao.get());

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occurred in deleting a cart by id. Error {}", e.getMessage());
            throw e;
        }
    }
}
