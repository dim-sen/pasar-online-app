package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.BatchDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface BatchRepository extends BaseRepository<BatchDao> {

    @Query("select b from BatchDao b where b.batchTime = :batch")
    Optional<BatchDao> findBatchDaoByBatchTime(@Param("batch") LocalTime batch);

    @Query("select b from BatchDao b")
    Page<BatchDao> pageableBatch(Pageable pageable);

    @Query("SELECT b FROM BatchDao b WHERE to_char(b.batchTime, 'HH24:MI') LIKE %:keyword%")
    Page<BatchDao> searchBatchDaoByKeyword(@Param("keyword") String keyword, Pageable pageable);

}