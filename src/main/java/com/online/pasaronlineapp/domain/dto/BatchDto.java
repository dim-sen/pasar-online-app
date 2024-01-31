package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.AdminDao;
import lombok.*;

import java.time.LocalTime;

/**
 * DTO for {@link com.online.pasaronlineapp.domain.dao.BatchDao}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BatchDto {

    Long id;
    LocalTime batchTime;
    @JsonIgnore
    boolean isActive;
    AdminDao adminDao;
}