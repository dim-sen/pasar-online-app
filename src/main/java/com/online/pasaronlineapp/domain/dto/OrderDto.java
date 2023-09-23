package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.online.pasaronlineapp.domain.dao.LocationDao;
import com.online.pasaronlineapp.domain.dao.PaymentDao;
import com.online.pasaronlineapp.domain.dao.UserDao;
import com.online.pasaronlineapp.domain.dao.WarehouseBatchDao;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {

    private Long id;

    private String orderStatus;

    private UserDao user;

    private WarehouseBatchDao warehouseBatch;

    private LocationDao location;

    private PaymentDao payment;
}