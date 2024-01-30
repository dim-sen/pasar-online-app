package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.CartDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends BaseRepository<CartDao> {

}