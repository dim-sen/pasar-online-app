package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.online.pasaronlineapp.domain.dao.BatchDao;
import com.online.pasaronlineapp.domain.dao.LocationDao;
import com.online.pasaronlineapp.domain.dao.PaymentDao;
import com.online.pasaronlineapp.domain.dao.UserDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {

    private Long id;

    private String orderStatus;

    private Integer totalPayment;

    private UserDao user;

    private LocationDao location;

    private PaymentDao payment;

    private BatchDao batch;
}