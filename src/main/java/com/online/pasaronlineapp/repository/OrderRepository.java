package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.OrderDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDao, Long> {
}