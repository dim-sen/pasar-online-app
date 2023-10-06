package com.online.pasaronlineapp.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.online.pasaronlineapp.domain.common.BaseDao;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "package_id")
    @ToString.Exclude
    private PackageDao packages;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private ItemDao item;
}
