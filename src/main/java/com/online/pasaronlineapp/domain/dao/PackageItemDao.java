package com.online.pasaronlineapp.domain.dao;

import com.online.pasaronlineapp.domain.common.BaseDao;
import com.online.pasaronlineapp.domain.common.PackageItemKey;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PACKAGES_ITEMS")
@SQLDelete(sql = "UPDATE PACKAGES_ITEMS SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@IdClass(PackageItemKey.class)
public class PackageItemDao extends BaseDao {

    @Id
    @Column(name = "package_id", nullable = false)
    private Long packageId;

    @Id
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @ManyToOne
    @MapsId(value = "packageId")
    @JoinColumn(name = "package_id")
    @ToString.Exclude
    private PackageDao packages;

    @ManyToOne
    @MapsId(value = "itemId")
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private ItemDao item;
}
