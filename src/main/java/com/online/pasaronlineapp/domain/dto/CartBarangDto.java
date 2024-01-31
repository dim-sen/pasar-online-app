package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.BarangDao;
import com.online.pasaronlineapp.domain.dao.CartDao;
import lombok.*;

/**
 * DTO for {@link com.online.pasaronlineapp.domain.dao.CartBarangDao}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CartBarangDto {

    Long id;
    Integer quantity;
    String status;
    @JsonIgnore
    boolean isActive;
    CartDao cartDao;
    BarangDao barangDao;
}