package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.ItemDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends BaseRepository<ItemDao> {

    @Query("select i from ItemDao i where i.itemName like concat('%', ?1, '%')")
    Optional<ItemDao> findItemName(String itemName);

    @Query("select i from ItemDao i")
    Page<ItemDao> pageableItem(Pageable pageable);

    @Query("select i from ItemDao i where lower(i.itemName) like %:keyword% or lower(i.categoryDao.categoryName) like %:keyword%")
    Page<ItemDao> searchItemDaoByKeyword(@Param("keyword") String keyword,
                                         Pageable pageable);
}
