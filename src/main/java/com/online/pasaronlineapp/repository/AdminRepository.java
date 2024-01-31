package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.AdminDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminDao, Long> {

    @Query("select a from AdminDao a where a.username = :username")
    Optional<AdminDao> findByUsername(@Param("username") String username);
}
