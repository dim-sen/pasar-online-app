package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {

    private Long id;

    @Size(min = 3, max = 15, message = "Invalid First Name (3-15 characters)")
    private String firstName;

    @Size(min = 3, max = 15, message = "Invalid Last Name (3-15 characters)")
    private String lastName;

    @Size(min = 11, max = 12, message = "Invalid Phone Number (11 or 12 characters)")
    private String phoneNumber;

    private String password;

}
