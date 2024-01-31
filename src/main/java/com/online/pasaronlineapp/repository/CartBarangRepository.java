package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.CartBarangDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartBarangRepository extends JpaRepository<CartBarangDao, Long> {
}
