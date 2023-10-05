package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.BatchDao;
import com.online.pasaronlineapp.domain.dao.WarehouseBatchDao;
import com.online.pasaronlineapp.domain.dto.WarehouseBatchDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.exception.DataNotFoundException;
import com.online.pasaronlineapp.repository.WarehouseBatchRepository;
import com.online.pasaronlineapp.service.WarehouseBatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WarehouseBatchServiceImpl implements WarehouseBatchService {

    @Autowired
    private WarehouseBatchRepository warehouseBatchRepository;

    @Override
    public void createWarehouseBatch(WarehouseBatchDto warehouseBatchDto) {
        try {
            log.info("Creating new Warehouse-Batch");
            List<BatchDao> batchDaoList = warehouseBatchDto.getBatchList();

            for (BatchDao batchDao : batchDaoList) {
                log.info("find By Warehouse Id And Batch Id");
                Optional<WarehouseBatchDao> optionalWarehouseBatchDao = warehouseBatchRepository.
                        findByWarehouseIdAndBatchId(warehouseBatchDto.getWarehouseDao().getId(),
                                batchDao.getId()
                        );

                if (optionalWarehouseBatchDao.isPresent()) {
                    log.info("Warehouse-Batch already exist");
                    throw new AlreadyExistException("Warehouse-Batch Already Exist");
                }

                log.info("Found it and save it");
                WarehouseBatchDao dao = new WarehouseBatchDao();
                dao.setWarehouse(warehouseBatchDto.getWarehouseDao());
                dao.setBatch(batchDao);

                warehouseBatchRepository.save(dao);
            }
        } catch (Exception e) {
            log.error("An error occurred in creating Warehouse-Batch. Error {}", e.getMessage());
            throw e;
        }
    }

//    @Override
//    public void createWarehouseBatch(WarehouseBatchDto warehouseBatchDto) {
//        try {
//            log.info("Creating new Warehouse-Batch");
//            List<BatchDao> batchDaoList = warehouseBatchDto.getBatchList();
//
//            for (BatchDao batchDao : batchDaoList) {
//                log.info("find By Warehouse Id And Batch Id");
//                Optional<WarehouseBatchDao> optionalWarehouseBatchDao = warehouseBatchRepository.
//                        findByWarehouseIdAndBatchId(warehouseBatchDto.getWarehouse().getId(),
//                                batchDao.getId()
//                        );
//
//                if (optionalWarehouseBatchDao.isPresent()) {
//                    log.info("Warehouse-Batch already exist");
//                    throw new AlreadyExistException("Warehouse-Batch Already Exist");
//                }
//
//                log.info("Found it and save it");
//                WarehouseBatchDao warehouseBatchDao = WarehouseBatchDao.builder()
//                        .warehouse(warehouseBatchDto.getWarehouse())
//                        .batch(batchDao)
//                        .build();
//
//                warehouseBatchRepository.save(warehouseBatchDao);
//            }
//        } catch (Exception e) {
//            log.error("An error occurred in creating Warehouse-Batch. Error {}", e.getMessage());
//            throw e;
//        }
//    }

    @Override
    public WarehouseBatchDao findWarehouseBatchById(Long id) {
        try {
            log.info("Finding a Warehouse-Batch by id");
            Optional<WarehouseBatchDao> optionalWarehouseBatchDao = warehouseBatchRepository.findWarehouseBatchDaoById(id);

            if (optionalWarehouseBatchDao.isEmpty()) {
                log.info("Warehouse-Batch not found");
                throw new DataNotFoundException("Warehouse-Batch not Found");
            }

            log.info("Warehouse-Batch Found");
            return optionalWarehouseBatchDao.get();
        } catch (DataNotFoundException e) {
            log.error("An error occurred in finding Warehouse-Batch. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateWarehouseBatch(WarehouseBatchDto warehouseBatchDto) {
        try {
            log.info("Updating a Warehouse-Batch by id");
            Optional<WarehouseBatchDao> optionalWarehouseBatchDao = warehouseBatchRepository.findById(warehouseBatchDto.getId());

            Optional<WarehouseBatchDao> warehouseBatchDaoOptional = warehouseBatchRepository.
                    findByWarehouseIdAndBatchId(warehouseBatchDto.getWarehouseDao().getId(),
                            warehouseBatchDto.getBatchDao().getId());

            log.info("warehouseBatchDto.getWarehouseDao().getId(): " + warehouseBatchDto.getWarehouseDao().getId());
            log.info("warehouseBatchDto.getBatchDao().getId(): " + warehouseBatchDto.getBatchDao().getId());

            if (warehouseBatchDaoOptional.isPresent() && !warehouseBatchDaoOptional.get().getId().equals(warehouseBatchDto.getId())) {
                log.info("Warehouse-Batch already exist");
                throw new AlreadyExistException("Warehouse-Batch Already Exist");
            }

            log.info("Warehouse-Batch Found");
            WarehouseBatchDao warehouseBatchDao = optionalWarehouseBatchDao.get();
            warehouseBatchDao.setWarehouse(warehouseBatchDto.getWarehouseDao());
            warehouseBatchDao.setBatch(warehouseBatchDto.getBatchDao());
            warehouseBatchRepository.save(warehouseBatchDao);

        } catch (Exception e) {
            log.error("An error occurred in updating Warehouse-Batch by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void inactiveWarehouseBatchById(Long id) {
        try {
            log.info("Inactivating Warehouse-Batch by id");
            Optional<WarehouseBatchDao> optionalWarehouseBatchDao = warehouseBatchRepository.findById(id);

            if (optionalWarehouseBatchDao.isEmpty()) {
                log.info("Warehouse-Batch not found");
                throw new DataNotFoundException("Warehouse-Batch not Found");
            }

            log.info("Warehouse-Batch Found");
            warehouseBatchRepository.updateIsActive(id, !optionalWarehouseBatchDao.get().isActive());

        } catch (Exception e) {
            log.error("An error occurred in inactivating Warehouse-Batch by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<WarehouseBatchDto> warehouseBatchPage(Integer pageNumber) {
        try {
            log.info("Showing Warehouse-Batch pagination");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<WarehouseBatchDao> warehouseBatchDaoPage = warehouseBatchRepository.pageableWarehouseBatch(pageable);

            for (WarehouseBatchDao warehouseBatchDao : warehouseBatchDaoPage.getContent()) {
                log.info("warehouseName1: " + warehouseBatchDao.getWarehouse().getWarehouseName());
                log.info("batchTime1: " + warehouseBatchDao.getBatch().getBatchTime());
            }

            return warehouseBatchDaoPage.<WarehouseBatchDto>map(warehouseBatchDao -> WarehouseBatchDto.builder()
                    .id(warehouseBatchDao.getId())
                    .warehouseDao(warehouseBatchDao.getWarehouse())
                    .batchDao(warehouseBatchDao.getBatch())
                    .isActive(warehouseBatchDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in showing pagination. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<WarehouseBatchDto> searchWarehouseBatch(String keyword, Integer pageNumber) {
        try {
            log.info("Searching for Warehouse-Batch");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            log.info("keyword: " + keyword.toLowerCase());

            Page<WarehouseBatchDao> warehouseBatchDaoPage = warehouseBatchRepository.searchWarehouseBatchDaoByKeyword(keyword.toLowerCase(), pageable);

            for (WarehouseBatchDao warehouseBatchDao : warehouseBatchDaoPage.getContent()) {
                log.info("warehouseName2: " + warehouseBatchDao.getWarehouse().getWarehouseName());
                log.info("batchTime2: " + warehouseBatchDao.getBatch().getBatchTime());
            }

            return warehouseBatchDaoPage.<WarehouseBatchDto>map(warehouseBatchDao -> WarehouseBatchDto.builder()
                    .id(warehouseBatchDao.getId())
                    .warehouseDao(warehouseBatchDao.getWarehouse())
                    .batchDao(warehouseBatchDao.getBatch())
                    .isActive(warehouseBatchDao.isActive())
                    .build());
        } catch (Exception e) {
            log.info("An error occurred in searching for Warehouse-Batch. Error {}", e.getMessage());
            throw e;
        }
    }
}
