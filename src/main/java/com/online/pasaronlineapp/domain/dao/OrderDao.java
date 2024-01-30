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
@Table(name = "ORDERS")
public class OrderDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @Column(name = "total_payment", nullable = false)
    private Integer totalPayment;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    @ToString.Exclude
    private LocationDao location;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouseBatch_id")
    @ToString.Exclude
    private WarehouseBatchDao warehouseBatchDao;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    @ToString.Exclude
    private AdminDao adminDao;

    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<OrderDetailDao> orderDetailDaos;
}
