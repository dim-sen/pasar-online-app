package com.online.pasaronlineapp.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.online.pasaronlineapp.domain.common.BaseDao;
import com.online.pasaronlineapp.util.LocalTImeAttributeConverter;
import lombok.*;

import javax.persistence.*;
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

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    @ToString.Exclude
    private AdminDao adminDao;

    @OneToMany(mappedBy = "batchDao", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<WarehouseBatchDao> warehouseBatchDaos;
}
