package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.LocationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationDao, Long> {
}