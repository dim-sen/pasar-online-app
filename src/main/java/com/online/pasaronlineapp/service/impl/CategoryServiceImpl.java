package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.CategoryDao;
import com.online.pasaronlineapp.domain.dto.CategoryDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.exception.DataNotFoundException;
import com.online.pasaronlineapp.repository.CategoryRepository;
import com.online.pasaronlineapp.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void createCategory(CategoryDto categoryDto) {
        try {
            log.info("Creating new category");
            Optional<CategoryDao> optionalCategoryDao = categoryRepository.findCategoryDaoByCategoryName(categoryDto.getCategoryName());

            if (optionalCategoryDao.isPresent()) {
                log.info("Category already exists");
                throw new AlreadyExistException("Category Already Exist");
            }

            CategoryDao categoryDao = CategoryDao.builder()
                    .categoryName(categoryDto.getCategoryName())
                    .build();

            categoryRepository.save(categoryDao);

        } catch (Exception e) {
            log.error("An error occurred in creating new Category. Error: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public CategoryDao findCategoryById(Long id) {
        log.info("Finding a category by id");
        Optional<CategoryDao> optionalCategoryDao = categoryRepository.findById(id);

        if (optionalCategoryDao.isEmpty()) {
            log.info("Category not Found");
            return null;
        }

        log.info("Category Found");
        return optionalCategoryDao.get();
    }

    @Override
    public List<CategoryDto> getAllCategories() {
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

            return categoryDtoList;

        } catch (Exception e) {
            log.error("An error occurred in finding all categories. Error {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void updateCategoryById(CategoryDto categoryDto) {
        try {
            log.info("Updating a category by id");
            Optional<CategoryDao> optionalCategoryDao = categoryRepository.findById(categoryDto.getId());

            Optional<CategoryDao> categoryDaoByCategoryName = categoryRepository.findCategoryDaoByCategoryName(categoryDto.getCategoryName());
            if (categoryDaoByCategoryName.isPresent() && !categoryDaoByCategoryName.get().getId().equals(categoryDto.getId())) {
                log.info("Category already exist");
                throw new AlreadyExistException("Category Already Exist");
            }

            log.info("Category found");
            CategoryDao categoryDao = optionalCategoryDao.get();
            categoryDao.setCategoryName(categoryDto.getCategoryName());
            categoryRepository.save(categoryDao);

        } catch (Exception e) {
            log.error("An error occurred in updating category by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteCategoryById(Long id) {
        try {
            log.info("Deleting category by id");
            Optional<CategoryDao> optionalCategoryDao = categoryRepository.findById(id);

            if (optionalCategoryDao.isEmpty()) {
                log.info("Category not found");
                throw new DataNotFoundException("Category Not Found");
            }

            log.info("Category found");
            categoryRepository.updateIsActive(id, !optionalCategoryDao.get().isActive());
        } catch (Exception e) {
            log.error("An error occurred in deleting category by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<CategoryDto> categoryPage(Integer pageNumber) {
        try {
            log.info("Showing categories pagination");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by("isActive").descending());

            Page<CategoryDao> categoryDaoPage = categoryRepository.pageableCategory(pageable);

            return categoryDaoPage.<CategoryDto>map(categoryDao -> CategoryDto.builder()
                    .id(categoryDao.getId())
                    .categoryName(categoryDao.getCategoryName())
                    .isActive(categoryDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in showing categories. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<CategoryDto> searchCategory(String keyword, Integer pageNumber) {
        try {
            log.info("Searching for category");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by("isActive").descending());

            Page<CategoryDao> categoryDaoPage = categoryRepository.searchCategoryDaoByKeyword(keyword, pageable);

            return categoryDaoPage.<CategoryDto>map(categoryDao -> CategoryDto.builder()
                    .id(categoryDao.getId())
                    .categoryName(categoryDao.getCategoryName())
                    .isActive(categoryDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in searching for categories. Error {}", e.getMessage());
            throw e;
        }
    }
}
