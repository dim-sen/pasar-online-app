package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.CategoryDao;
import com.online.pasaronlineapp.domain.dto.CategoryDto;
import com.online.pasaronlineapp.repository.CategoryRepository;
import com.online.pasaronlineapp.service.CategoryRestService;
import com.online.pasaronlineapp.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CategoryRestServiceImpl implements CategoryRestService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<Object> getAllCategories() {
        try {
            log.info("Getting all categories");
            List<CategoryDao> categoryDaoList = categoryRepository.findAll();
            List<CategoryDto> categoryDtoList = new ArrayList<>();

            for (CategoryDao categoryDao : categoryDaoList) {
                categoryDtoList.add(CategoryDto.builder()
                                .id(categoryDao.getId())
                                .categoryName(categoryDao.getCategoryName())
                        .build());
            }
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, categoryDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all categories. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
