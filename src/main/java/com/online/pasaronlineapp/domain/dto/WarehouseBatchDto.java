package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.BatchDao;
import com.online.pasaronlineapp.domain.dao.WarehouseDao;
import lombok.*;

import java.util.List;

/**
 * DTO for {@link com.online.pasaronlineapp.domain.dao.WarehouseBatchDao}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WarehouseBatchDto {

    Long id;
    WarehouseDao warehouseDao;
    BatchDao batchDao;
    @JsonIgnore
    boolean isActive;

    @JsonIgnore
    List<BatchDao> batchDaoList;
}