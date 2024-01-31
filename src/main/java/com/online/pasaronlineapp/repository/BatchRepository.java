package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.BatchDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<BatchDao, Long> {

    @Query("select b from BatchDao b where b.batchTime = :batch")
    Optional<BatchDao> findBatchDaoByBatchTime(@Param("batch") LocalTime batch);

    @Query("select b from BatchDao b")
    Page<BatchDao> pageableBatch(Pageable pageable);

    @Query("select b from BatchDao b where to_char(b.batchTime, 'HH24:MI') like %:keyword%")
    Page<BatchDao> searchBatchDaoByKeyword(@Param("keyword") String keyword, Pageable pageable);

}