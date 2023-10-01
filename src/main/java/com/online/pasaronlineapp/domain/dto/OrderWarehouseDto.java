package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.online.pasaronlineapp.domain.dao.OrderDao;
import com.online.pasaronlineapp.domain.dao.WarehouseBatchDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderWarehouseDto {

    private Long id;

    private OrderDao order;

    private WarehouseBatchDao warehouseBatch;
}