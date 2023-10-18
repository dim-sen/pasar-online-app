package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.BatchDao;
import com.online.pasaronlineapp.domain.dto.BatchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BatchService {

    void createBatch(BatchDto batchDto);

    BatchDao findBatchById(Long id);

    List<BatchDto> getAllBatches();

    void updateBatch(BatchDto batchDto);

    void inactivateBatchById(Long id);

    Page<BatchDto> batchPage(Integer pageNumber);

    Page<BatchDto> searchBatch(String keyword, Integer pageNumber);
}
