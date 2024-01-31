package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.WarehouseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseDao, Long> {

    @Query("select w from WarehouseDao w where w.warehouseName = :name")
    Optional<WarehouseDao> findWarehouseDaoByWarehouseName(@Param("name") String name);

    @Query("select w from WarehouseDao w")
    Page<WarehouseDao> pageableWarehouse(Pageable pageable);

    @Query("select w from WarehouseDao w where lower(w.warehouseName) like %:keyword% or lower(w.warehouseAddress) like %:keyword%")
    Page<WarehouseDao> searchWarehouseDaoByKeyword(@Param(value = "keyword") String keyword, Pageable pageable);
}