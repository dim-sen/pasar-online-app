package com.online.pasaronlineapp.domain.dao;

import com.online.pasaronlineapp.domain.common.BaseDao;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CARTS")
public class CartDao extends BaseDao {

    @Id
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserDao user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private ItemDao item;

    @ManyToOne
    @JoinColumn(name = "package_item_id")
    @ToString.Exclude
    private PackageItemDao packageItem;
}
