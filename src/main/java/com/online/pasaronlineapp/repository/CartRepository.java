package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.CartDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends BaseRepository<CartDao> {

    @Query("select c from CartDao c " +
            "where c.user.id = :user_id and c.item.id = :item_id and c.packageItem.id = :packageItem_id")
    Optional<CartDao> findByUserIdAndItemIdAndPackageItemId(@Param("user_id") Long user_id, @Param("item_id") Long item_id, @Param("packageItem_id") Long packageItem_id);
}