package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.AdminDao;
import com.online.pasaronlineapp.domain.dao.LocationDao;
import com.online.pasaronlineapp.domain.dao.WarehouseBatchDao;
import lombok.*;

/**
 * DTO for {@link com.online.pasaronlineapp.domain.dao.OrderDao}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderDto {

    Long id;
    String orderStatus;
    Integer totalPayment;
    LocationDao location;

    @JsonIgnore
    boolean isActive;
    WarehouseBatchDao warehouseBatchDao;
    AdminDao adminDao;
}