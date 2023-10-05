package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.WarehouseBatchDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseBatchRepository extends BaseRepository<WarehouseBatchDao> {

    @Query("select wb from WarehouseBatchDao wb where wb.warehouse.id = :warehouseId and wb.batch.id = :batchId")
    Optional<WarehouseBatchDao> findByWarehouseIdAndBatchId(@Param("warehouseId") Long warehouseId, @Param("batchId") Long batchId);

    @Query("select w from WarehouseBatchDao w where w.id = :id")
    Optional<WarehouseBatchDao> findWarehouseBatchDaoById(@Param("id") Long id);

    @Query("select wb from WarehouseBatchDao wb")
    Page<WarehouseBatchDao> pageableWarehouseBatch(Pageable pageable);

    @Query("select wb from WarehouseBatchDao wb where lower(wb.warehouse.warehouseName) like %:keyword% or to_char(wb.batch.batchTime, 'HH24:MI') like %:keyword%")
    Page<WarehouseBatchDao> searchWarehouseBatchDaoByKeyword(@Param("keyword") String keyword, Pageable pageable);
}