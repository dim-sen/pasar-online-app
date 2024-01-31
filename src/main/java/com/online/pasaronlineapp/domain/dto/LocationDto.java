package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.PembeliDao;
import lombok.*;

/**
 * DTO for {@link com.online.pasaronlineapp.domain.dao.LocationDao}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LocationDto {

    Long id;
    String locationName;
    String province;
    String city;
    String subDistrict;
    String streetName;
    String detailAddress;
    String latitude;
    String longitude;
    @JsonIgnore
    boolean isActive;
    PembeliDao pembeli;
}