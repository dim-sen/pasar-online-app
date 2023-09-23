package com.online.pasaronlineapp.domain.dao;

import com.online.pasaronlineapp.domain.common.BaseDao;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ORDERS_DETAILS")
public class OrderDetailDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private OrderDao order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private ItemDao item;

    @ManyToOne
    @JoinColumn(name = "package_item_id")
    @ToString.Exclude
    private PackageItemDao packageItem;
}
