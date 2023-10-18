package com.online.pasaronlineapp.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.online.pasaronlineapp.domain.common.BaseDao;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "WAREHOUSES")
public class WarehouseDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "warehouse_name", nullable = false)
    private String warehouseName;

    @Column(name = "warehouse_address", nullable = false)
    private String warehouseAddress;

    @OneToMany(mappedBy = "warehouseDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<WarehouseBatchDao> warehouseBatchDaos;
}
