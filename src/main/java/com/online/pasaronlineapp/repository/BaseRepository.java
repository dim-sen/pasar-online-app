package com.online.pasaronlineapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {

    @Modifying
    @Transactional
    @Query("update #{#entityName} e set e.isActive = :isActive, e.updatedAt = :updatedAt where e.id = :id")
    void updateIsActive(@Param("id") Long id, @Param("isActive") Boolean isActive, @Param("updatedAt") LocalDateTime updatedAt);

    @Query("select count(e) > 0 from #{#entityName} e where e.id = :id and e.isActive = false")
    boolean checkIfIsActiveFalse(@Param("id") Long id);
}
