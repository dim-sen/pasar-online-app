package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.BatchDao;
import com.online.pasaronlineapp.domain.dao.WarehouseBatchDao;
import com.online.pasaronlineapp.domain.dto.BatchDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.exception.DataNotFoundException;
import com.online.pasaronlineapp.repository.BatchRepository;
import com.online.pasaronlineapp.repository.WarehouseBatchRepository;
import com.online.pasaronlineapp.service.BatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private WarehouseBatchRepository warehouseBatchRepository;

    @Override
    public void createBatch(BatchDto batchDto) {
        try {
            log.info("Creating new batch");
            Optional<BatchDao> optionalBatchDao = batchRepository.findBatchDaoByBatchTime(batchDto.getBatchTime());

            if (optionalBatchDao.isPresent()) {
                log.info("Batch already exists");
                throw new AlreadyExistException("Batch Already Exist");
            }

            BatchDao batchDao = BatchDao.builder()
                    .batchTime(batchDto.getBatchTime())
                    .build();

            batchRepository.save(batchDao);
        } catch (Exception e) {
            log.error("An error occurred in creating new batch. Error: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public BatchDao findBatchById(Long id) {
        try {
            log.info("Finding a batch by id");
            Optional<BatchDao> optionalBatchDao = batchRepository.findById(id);

            if (optionalBatchDao.isEmpty()) {
                log.info("Batch not Found");
                throw new DataNotFoundException("Batch not Found");
            }

            log.info("Batch Found");
            return optionalBatchDao.get();
        } catch (Exception e) {
            log.error("An error occurred in finding a batch by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BatchDto> getAllBatches() {
        try {
            log.info("Getting all batches");
            List<BatchDao> batchDaoList = batchRepository.findAll();
            List<BatchDto> batchDtoList = new ArrayList<>();

            for (BatchDao batchDao : batchDaoList) {
                batchDtoList.add(BatchDto.builder()
                                .id(batchDao.getId())
                                .batchTime(batchDao.getBatchTime())
                        .build());
            }

            return batchDtoList;
        } catch (Exception e) {
            log.error("An error occurred in getting all batches. Error {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void updateBatch(BatchDto batchDto) {
        try {
            log.info("Updating a batch by id");
            Optional<BatchDao> optionalBatchDao = batchRepository.findById(batchDto.getId());

            Optional<BatchDao> batchDaoOptional = batchRepository.findBatchDaoByBatchTime(batchDto.getBatchTime());

            if (batchDaoOptional.isPresent() && !batchDaoOptional.get().getId().equals(batchDto.getId())) {
                log.info("Batch already exist");
                throw new AlreadyExistException("Batch Already Exist");
            }

            log.info("Batch found");
            BatchDao batchDao = optionalBatchDao.get();
            batchDao.setBatchTime(batchDto.getBatchTime());
            batchRepository.save(batchDao);

        } catch (Exception e) {
            log.error("An error occurred in updating batch by id. Error {}", e.getMessage());
            throw e;
        }

    }

    @Override
    public void inactivateBatchById(Long id) {
        try {
            log.info("Inactivating batch by id");
            Optional<BatchDao> optionalBatchDao = batchRepository.findById(id);

            if (optionalBatchDao.isEmpty()) {
                log.info("Batch not found");
                throw new DataNotFoundException("Batch Not Found");
            }

            log.info("Batch found");
            if (optionalBatchDao.get().isActive()) {
                List<WarehouseBatchDao> warehouseBatchDaoList = warehouseBatchRepository.findAllByBatchId(id);
                for (WarehouseBatchDao warehouseBatchDao : warehouseBatchDaoList) {
                    if (warehouseBatchDao.isActive()) {
                        warehouseBatchRepository.updateIsActive(warehouseBatchDao.getId(), false, LocalDateTime.now());
                    }
                }
                batchRepository.updateIsActive(id, false, LocalDateTime.now());
            } else {
                batchRepository.updateIsActive(id, true, LocalDateTime.now());
            }

        } catch (Exception e) {
            log.error("An error occurred in inactivating batch by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<BatchDto> batchPage(Integer pageNumber) {
        try {
            log.info("Showing batches pagination");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.asc("batchTime")));

            Page<BatchDao> batchDaoPage = batchRepository.pageableBatch(pageable);

            return batchDaoPage.<BatchDto>map(batchDao -> BatchDto.builder()
                    .id(batchDao.getId())
                    .batchTime(batchDao.getBatchTime())
                    .isActive(batchDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in showing batches. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<BatchDto> searchBatch(String keyword, Integer pageNumber) {
        try {
            log.info("Searching for batch");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<BatchDao> batchDaoPage = batchRepository.searchBatchDaoByKeyword(keyword, pageable);

            return batchDaoPage.<BatchDto>map(batchDao -> BatchDto.builder()
                    .id(batchDao.getId())
                    .batchTime(batchDao.getBatchTime())
                    .isActive(batchDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in searching for batch. Error {}", e.getMessage());
            throw e;
        }
    }
}
