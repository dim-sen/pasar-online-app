package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.UserDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<UserDao> {

    @Query("select u from UserDao u where u.phoneNumber = :username")
    Optional<UserDao> findByPhoneNumber(@Param("username") String username);

    @Query("select (count(u) > 0) from UserDao u where u.phoneNumber = :phoneNumber")
    boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
