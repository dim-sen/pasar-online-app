package com.online.pasaronlineapp.domain.common;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserItemKey implements Serializable {

    private Long userId;

    private Long itemId;
}
