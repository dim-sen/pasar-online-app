package com.online.pasaronlineapp.domain.comman;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PackageItemKey implements Serializable {

    private Long packageId;

    private Long itemId;
}
