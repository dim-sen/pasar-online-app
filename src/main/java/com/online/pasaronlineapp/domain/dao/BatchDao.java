package com.online.pasaronlineapp.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.online.pasaronlineapp.domain.common.BaseDao;
import com.online.pasaronlineapp.util.LocalTImeAttributeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Column(name = "batch_time", columnDefinition = "time")
    @Convert(converter = LocalTImeAttributeConverter.class)
    private LocalTime batchTime;

    @OneToMany(mappedBy = "batch")
    @ToString.Exclude
    @JsonIgnore
    private List<WarehouseBatchDao> warehouseBatchDaos;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<OrderDao> orderDaos;
}
