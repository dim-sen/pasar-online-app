package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.BarangDao;
import com.online.pasaronlineapp.domain.dao.PackageDao;
import lombok.*;

import java.util.List;

/**
 * DTO for {@link com.online.pasaronlineapp.domain.dao.PackageBarangDao}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PackageBarangDto {
    Long id;
    PackageDao packageDao;
    BarangDao barangDao;
    @JsonIgnore
    boolean isActive;

    @JsonIgnore
    List<BarangDao> barangDaoList;
}