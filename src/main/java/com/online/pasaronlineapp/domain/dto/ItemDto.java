package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.CategoryDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemDto {

    private Long id;

    @Size(min = 3, message = "Invalid Item Name (min 3 characters)")
    private String itemName;

    @Min(value = 1, message = "Invalid Item Price (> 0)")
    private Integer itemPrice;

    @Min(value = 1, message = "Invalid Item Weight (> 0)")
    private Integer itemWeight;

    private String itemImage;

    private CategoryDao categoryDao;
}
