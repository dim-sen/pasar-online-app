package com.online.pasaronlineapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {

    @Modifying
    @Transactional
    @Query("update #{#entityName} e set e.isActive = :isActive where e.id = :id")
    void updateIsActive(@Param("id") Long id, @Param("isActive") Boolean isActive);
}
