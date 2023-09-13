package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.PackageItemDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PackageDto {

    private Long id;

    @Size(min = 3, message = "Invalid Name (min 3 characters)")
    private String packageName;

    @Min(value = 1, message = "Invalid Price (> 0)")
    private Integer packagePrice;

    private String packageDescription;

    private String packageImage;

    private List<PackageItemDao> packageItemDaos;
}
