package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.WarehouseDao;
import com.online.pasaronlineapp.domain.dto.WarehouseDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.exception.DataNotFoundException;
import com.online.pasaronlineapp.repository.WarehouseRepository;
import com.online.pasaronlineapp.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public void createWarehouse(WarehouseDto warehouseDto) {
        try {
            log.info("Creating new warehouse");
            Optional<WarehouseDao> optionalWarehouseDao = warehouseRepository.findWarehouseDaoByWarehouseName(warehouseDto.getWarehouseName());

            if (optionalWarehouseDao.isPresent()) {
                log.info("Warehouse already exist");
                throw new AlreadyExistException("Warehouse Already Exist");
            }

            WarehouseDao warehouseDao = WarehouseDao.builder()
                    .warehouseName(warehouseDto.getWarehouseName())
                    .warehouseAddress(warehouseDto.getWarehouseAddress())
                    .build();

            warehouseRepository.save(warehouseDao);
        } catch (Exception e) {
            log.info("An error occurred in creating new warehouse. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public WarehouseDao findWarehouseById(Long id) {
        try {
            log.info("Finding a warehouse by id");
            Optional<WarehouseDao> optionalWarehouseDao = warehouseRepository.findById(id);

            if (optionalWarehouseDao.isEmpty()) {
                log.info("Warehouse not Found");
                throw new DataNotFoundException("Warehouse not Found");
            }
            log.info("Warehouse Found");

            return optionalWarehouseDao.get();
        } catch (Exception e) {
            log.error("An error occurred in finding a warehouse by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateWarehouse(WarehouseDto warehouseDto) {
        try {
            log.info("Updating a warehouse by id");
            Optional<WarehouseDao> optionalWarehouseDao = warehouseRepository.findById(warehouseDto.getId());

            Optional<WarehouseDao> warehouseDaoOptional = warehouseRepository.findWarehouseDaoByWarehouseName(warehouseDto.getWarehouseName());

            if (warehouseDaoOptional.isPresent() && !warehouseDaoOptional.get().getId().equals(warehouseDto.getId())) {
                log.info("Warehouse already exist");
                throw new AlreadyExistException("Warehouse Already Exist");
            }

            log.info("Warehouse found");
            WarehouseDao warehouseDao = optionalWarehouseDao.get();
            warehouseDao.setWarehouseName(warehouseDto.getWarehouseName());
            warehouseDao.setWarehouseAddress(warehouseDto.getWarehouseAddress());
            warehouseRepository.save(warehouseDao);

        } catch (Exception e) {
            log.error("An error occurred in updating warehouse by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void inactivateWarehouseById(Long id) {
        try {
            log.info("Inactivating warehouse by id");
            Optional<WarehouseDao> optionalWarehouseDao = warehouseRepository.findById(id);

            if (optionalWarehouseDao.isEmpty()) {
                log.info("Warehouse not found");
                throw new DataNotFoundException("Warehouse Not Found");
            }

            log.info("Warehouse found");
            warehouseRepository.updateIsActive(id, !optionalWarehouseDao.get().isActive());
        } catch (Exception e) {
            log.error("An error occurred in inactivating warehouse by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<WarehouseDto> warehousePage(Integer pageNumber) {
        try {
            log.info("Showing warehouse pagination");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by("isActive").descending());

            Page<WarehouseDao> warehouseDaoPage = warehouseRepository.pageableWarehouse(pageable);

            return warehouseDaoPage.<WarehouseDto>map(warehouseDao -> WarehouseDto.builder()
                    .id(warehouseDao.getId())
                    .warehouseName(warehouseDao.getWarehouseName())
                    .warehouseAddress(warehouseDao.getWarehouseAddress())
                    .isActive(warehouseDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in showing warehouses. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<WarehouseDto> searchWarehouse(String keyword, Integer pageNumber) {
        try {
            log.info("Searching for warehouse");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by("isActive").descending());

            Page<WarehouseDao> warehouseDaoPage = warehouseRepository.searchWarehouseDaoByKeyword(keyword.toLowerCase(), pageable);

            return warehouseDaoPage.<WarehouseDto>map(warehouseDao -> WarehouseDto.builder()
                    .id(warehouseDao.getId())
                    .warehouseName(warehouseDao.getWarehouseName())
                    .warehouseAddress(warehouseDao.getWarehouseAddress())
                    .isActive(warehouseDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in searching for warehouse. Error {}", e.getMessage());
            throw e;
        }
    }
}
