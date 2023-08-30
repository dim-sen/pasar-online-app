package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.RoleDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleDao, Long> {

    @Query("select r from RoleDao r where r.name = :name")
    RoleDao findByName(@Param("name") String name);
}
