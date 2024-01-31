package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.OrderDetailDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailDao, Long> {
}