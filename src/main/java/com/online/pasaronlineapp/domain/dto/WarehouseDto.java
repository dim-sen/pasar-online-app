package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.AdminDao;
import lombok.*;

/**
 * DTO for {@link com.online.pasaronlineapp.domain.dao.WarehouseDao}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WarehouseDto {

    Long id;
    String warehouseName;
    String warehouseAddress;
    String latitude;
    String longitude;
    AdminDao adminDao;
    @JsonIgnore
    boolean isActive;
}