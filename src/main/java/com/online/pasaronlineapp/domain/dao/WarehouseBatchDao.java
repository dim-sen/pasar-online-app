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
@Table(name = "WAREHOUSES_BATCHES")
public class WarehouseBatchDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id")
    @ToString.Exclude
    private WarehouseDao warehouseDao;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "batch_id")
    @ToString.Exclude
    private BatchDao batchDao;

    @OneToMany(mappedBy = "warehouseBatch", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<OrderWarehouseDao> orderDaos;
}
