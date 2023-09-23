package com.online.pasaronlineapp.domain.dao;

import com.online.pasaronlineapp.domain.common.BaseDao;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PACKAGES_ITEMS")
public class PackageItemDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "package_id")
    @ToString.Exclude
    private PackageDao packages;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private ItemDao item;

    @OneToMany(mappedBy = "packageItem")
    @ToString.Exclude
    private List<CartDao> cartDaos;

    @OneToMany(mappedBy = "packageItem")
    @ToString.Exclude
    private List<OrderDetailDao> orderDetailDaos;
}
