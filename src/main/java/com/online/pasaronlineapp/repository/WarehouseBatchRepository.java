package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.WarehouseBatchDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseBatchRepository extends JpaRepository<WarehouseBatchDao, Long> {

    @Query("select wb from WarehouseBatchDao wb where wb.warehouseDao.id = :warehouseId and wb.batchDao.id = :batchId")
    Optional<WarehouseBatchDao> findByWarehouseIdAndBatchId(@Param("warehouseId") Long warehouseId, @Param("batchId") Long batchId);

    @Query("select wb from WarehouseBatchDao wb")
    Page<WarehouseBatchDao> pageableWarehouseBatch(Pageable pageable);

    @Query("select wb from WarehouseBatchDao wb where lower(wb.warehouseDao.warehouseName) like %:keyword% or to_char(wb.batchDao.batchTime, 'HH24:MI') like %:keyword%")
    Page<WarehouseBatchDao> searchWarehouseBatchDaoByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("select wb from WarehouseBatchDao wb where wb.warehouseDao.id = :id")
    List<WarehouseBatchDao> findAllByWarehouseId(@Param("id") Long id);

    @Query("select wb from WarehouseBatchDao wb where wb.batchDao.id = :id")
    List<WarehouseBatchDao> findAllByBatchId(@Param("id") Long id);
}