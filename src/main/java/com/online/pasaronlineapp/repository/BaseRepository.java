package com.online.pasaronlineapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {

    @Modifying
    @Transactional
    @Query("update #{#entityName} e set e.isActive = :isActive where e.id = :id")
    void updateIsActive(@Param("id") Long id, @Param("isActive") Boolean isActive);

    @Query("select e from #{#entityName} e where e.isActive = true ")
    List<T> findByIsActiveTrue();
}
