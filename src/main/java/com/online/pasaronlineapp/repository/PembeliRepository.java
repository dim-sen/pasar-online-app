package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.PembeliDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PembeliRepository extends JpaRepository<PembeliDao, Long> {

    @Query("select u from PembeliDao u where u.phoneNumber = :username")
    Optional<PembeliDao> findByPhoneNumber(@Param("username") String username);

    @Query("select (count(u) > 0) from PembeliDao u where u.phoneNumber = :phoneNumber")
    boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
