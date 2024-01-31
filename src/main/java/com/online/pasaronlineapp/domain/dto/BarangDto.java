package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.CategoryDao;
import lombok.*;
/**
 * DTO for {@link com.online.pasaronlineapp.domain.dao.BarangDao}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BarangDto {

    Long id;
    String barangName;
    Integer barangPrice;
    Integer barangWeight;
    Integer barangStock;
    String barangDescription;
    String barangImage;
    @JsonIgnore
    boolean isActive;
    CategoryDao categoryDao;
    AdminDto adminDao;
}