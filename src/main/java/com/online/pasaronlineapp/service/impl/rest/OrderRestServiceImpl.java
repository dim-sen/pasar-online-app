package com.online.pasaronlineapp.service.impl.rest;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.OrderDao;
import com.online.pasaronlineapp.domain.dto.OrderDto;
import com.online.pasaronlineapp.repository.OrderRepository;
import com.online.pasaronlineapp.service.rest.OrderRestService;
import com.online.pasaronlineapp.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderRestServiceImpl implements OrderRestService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResponseEntity<Object> createOrder(OrderDto orderDto) {
        try {
            OrderDao orderDao = OrderDao.builder()
                    .orderStatus(orderDto.getOrderStatus())
                    .totalPayment(orderDto.getTotalPayment())
                    .build();

            orderRepository.save(orderDao);

            OrderDto dto = OrderDto.builder()
                    .id(orderDao.getId())
                    .orderStatus(orderDao.getOrderStatus())
                    .totalPayment(orderDao.getTotalPayment())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new Booking. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
