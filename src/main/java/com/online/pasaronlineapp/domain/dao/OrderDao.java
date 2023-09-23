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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserDao user;

    @ManyToOne
    @JoinColumn(name = "warehouse_batch_id")
    @ToString.Exclude
    private WarehouseBatchDao warehouseBatch;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @ToString.Exclude
    private LocationDao location;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @ToString.Exclude
    private PaymentDao payment;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    private List<OrderDetailDao> orderDetailDaos;
}
