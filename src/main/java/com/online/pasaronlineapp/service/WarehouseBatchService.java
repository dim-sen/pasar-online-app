package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.WarehouseBatchDao;
import com.online.pasaronlineapp.domain.dto.WarehouseBatchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface WarehouseBatchService {

    void createWarehouseBatch(WarehouseBatchDto warehouseBatchDto);

    WarehouseBatchDao findWarehouseBatchById(Long id);

    void updateWarehouseBatch(WarehouseBatchDto warehouseBatchDto);

    void inactiveWarehouseBatchById(Long id);

    Page<WarehouseBatchDto> warehouseBatchPage(Integer pageNumber);

    Page<WarehouseBatchDto> searchWarehouseBatch(String keyword, Integer pageNumber);
}
