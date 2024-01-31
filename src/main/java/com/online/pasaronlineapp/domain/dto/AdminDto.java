package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.online.pasaronlineapp.domain.dao.RoleDao;
import lombok.*;

/**
 * DTO for {@link com.online.pasaronlineapp.domain.dao.AdminDao}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AdminDto {
    Long id;
    String firstName;
    String lastName;
    String username;
    String password;
    @JsonIgnore
    boolean isActive;
    RoleDao role;
}