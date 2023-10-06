package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.PackageItemDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageItemRepository extends BaseRepository<PackageItemDao> {

    @Query("select p from PackageItemDao p where p.packages.id = :packageId and p.item.id = :itemId")
    Optional<PackageItemDao> findByPackagesIdAndItemId(@Param("packageId") Long packageId, @Param("itemId") Long itemId);

    @Query("select p from PackageItemDao p")
    Page<PackageItemDao> pageablePackageItem(Pageable pageable);

    @Query("select p from PackageItemDao p where lower(p.packages.packageName) like %:keyword% or lower(p.item.itemName) like %:keyword%")
    Page<PackageItemDao> searchPackageItemDaoBy(@Param("keyword") String keyword, Pageable pageable);
}