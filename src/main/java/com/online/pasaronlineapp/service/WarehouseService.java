package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.WarehouseDao;
import com.online.pasaronlineapp.domain.dto.WarehouseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WarehouseService {

    void createWarehouse(WarehouseDto warehouseDto);

    WarehouseDao findWarehouseById(Long id);

    List<WarehouseDto> getAllWarehouses();

    void updateWarehouse(WarehouseDto warehouseDto);

    void inactivateWarehouseById(Long id);

    Page<WarehouseDto> warehousePage(Integer pageNumber);

    Page<WarehouseDto> searchWarehouse(String keyword, Integer pageNumber);
}
