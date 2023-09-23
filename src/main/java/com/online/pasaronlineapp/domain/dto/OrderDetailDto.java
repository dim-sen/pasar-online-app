package com.online.pasaronlineapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dao.OrderDao;
import com.online.pasaronlineapp.domain.dao.PackageItemDao;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetailDto {

    private Long id;

    private OrderDao order;

    private ItemDao item;

    private PackageItemDao packageItem;
}