package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.BatchDao;
import com.online.pasaronlineapp.domain.dao.WarehouseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WarehouseBatchDto {

    private Long id;

    private WarehouseDao warehouseDao;

    private BatchDao batchDao;

    @JsonIgnore
    private List<BatchDao> batchList;

    @JsonIgnore
    private Boolean isActive;
}