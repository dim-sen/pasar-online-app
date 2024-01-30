package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.PackageBarangDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageItemRepository extends BaseRepository<PackageBarangDao> {

    @Query("select p from PackageBarangDao p where p.packageDao.id = :packageId and p.barangDao.id = :itemId")
    Optional<PackageBarangDao> findByPackagesIdAndItemId(@Param("packageId") Long packageId, @Param("itemId") Long itemId);

    @Query("select p from PackageBarangDao p")
    Page<PackageBarangDao> pageablePackageItem(Pageable pageable);

    @Query("select p from PackageBarangDao p where lower(p.packageDao.packageName) like %:keyword% or lower(p.barangDao.barangName) like %:keyword%")
    Page<PackageBarangDao> searchPackageItemDaoBy(@Param("keyword") String keyword, Pageable pageable);

    @Query("select p from PackageBarangDao p where p.packageDao.id = :id")
    List<PackageBarangDao> findAllByPackageId(@Param("id") Long id);

    @Query("select p from PackageBarangDao p where p.barangDao.id = :id")
    List<PackageBarangDao> findAllByItemId(@Param("id") Long id);
}