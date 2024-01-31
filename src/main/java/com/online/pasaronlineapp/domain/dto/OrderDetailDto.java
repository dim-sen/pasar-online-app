package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.BarangDao;
import com.online.pasaronlineapp.domain.dao.OrderDao;
import lombok.*;

/**
 * DTO for {@link com.online.pasaronlineapp.domain.dao.OrderDetailDao}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderDetailDto {

    Long id;
    OrderDao order;
    BarangDao barang;
    @JsonIgnore
    boolean isActive;
}