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

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @ToString.Exclude
    private WarehouseDao warehouse;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    @ToString.Exclude
    private BatchDao batch;

    @OneToMany(mappedBy = "warehouseBatch")
    @ToString.Exclude
    private List<OrderDao> orderDaos;
}
