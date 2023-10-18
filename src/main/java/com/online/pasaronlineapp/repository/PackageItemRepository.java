package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.PackageItemDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageItemRepository extends BaseRepository<PackageItemDao> {

    @Query("select p from PackageItemDao p where p.packageDao.id = :packageId and p.itemDao.id = :itemId")
    Optional<PackageItemDao> findByPackagesIdAndItemId(@Param("packageId") Long packageId, @Param("itemId") Long itemId);

    @Query("select p from PackageItemDao p")
    Page<PackageItemDao> pageablePackageItem(Pageable pageable);

    @Query("select p from PackageItemDao p where lower(p.packageDao.packageName) like %:keyword% or lower(p.itemDao.itemName) like %:keyword%")
    Page<PackageItemDao> searchPackageItemDaoBy(@Param("keyword") String keyword, Pageable pageable);

    @Query("select p from PackageItemDao p where p.packageDao.id = :id")
    List<PackageItemDao> findAllByPackageId(@Param("id") Long id);

    @Query("select p from PackageItemDao p where p.itemDao.id = :id")
    List<PackageItemDao> findAllByItemId(@Param("id") Long id);
}