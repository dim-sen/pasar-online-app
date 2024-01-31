package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.PackageDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<PackageDao, Long> {

    @Query("select p from PackageDao p where p.packageName = :name")
    Optional<PackageDao> findPackageDaoByPackageName(@Param("name") String name);

    @Query("select p from PackageDao p")
    Page<PackageDao> pageablePackage(Pageable pageable);

    @Query("select p from PackageDao p where lower(p.packageName) like %:keyword% or cast(p.packagePrice as string ) like %:keyword%")
    Page<PackageDao> searchPackageDaoByKeyword(@Param("keyword") String keyword, Pageable pageable);
}