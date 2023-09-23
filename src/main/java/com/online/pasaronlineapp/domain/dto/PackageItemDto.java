package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dao.PackageDao;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackageItemDto {

    private Long id;

    private PackageDao packages;

    private ItemDao item;
}