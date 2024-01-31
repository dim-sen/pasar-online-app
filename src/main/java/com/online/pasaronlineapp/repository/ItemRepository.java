package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.BarangDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<BarangDao, Long> {

    @Query("select i from BarangDao i where i.barangName like concat('%', ?1, '%')")
    Optional<BarangDao> findItemName(String itemName);

    @Query("select i from BarangDao i")
    Page<BarangDao> pageableItem(Pageable pageable);

    @Query("select i from BarangDao i where lower(i.barangName) like %:keyword% or lower(i.categoryDao.categoryName) like %:keyword%")
    Page<BarangDao> searchItemDaoByKeyword(@Param("keyword") String keyword,
                                           Pageable pageable);
}
