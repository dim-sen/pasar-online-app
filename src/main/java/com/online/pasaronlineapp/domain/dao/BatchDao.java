package com.online.pasaronlineapp.domain.dao;

import com.online.pasaronlineapp.domain.common.BaseDao;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BATCHES")
public class BatchDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "batch_time")
    private LocalDateTime batchTime;

    @OneToMany(mappedBy = "batch")
    @ToString.Exclude
    private List<WarehouseBatchDao> warehouseBatchDaos;
}
