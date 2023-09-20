package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.CategoryDao;
import com.online.pasaronlineapp.domain.dto.CategoryDto;
import com.online.pasaronlineapp.repository.CategoryRepository;
import com.online.pasaronlineapp.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public CategoryDao createCategory(CategoryDto categoryDto) {
        try {
            log.info("Creating new category");
            Optional<CategoryDao> optionalCategoryDao = categoryRepository.findCategoryDaoByCategoryName(categoryDto.getCategoryName());

            if (optionalCategoryDao.isPresent()) {
                log.info("Category already exists");
                return null;
            }

            CategoryDao categoryDao = CategoryDao.builder()
                    .categoryName(categoryDto.getCategoryName())
                    .build();

            return categoryRepository.save(categoryDao);

        } catch (Exception e) {
            log.error("An error occurred in creating new Category. Error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        try {
            log.info("Getting a category by id");
            Optional<CategoryDao> optionalCategoryDao = categoryRepository.findById(id);

            if (optionalCategoryDao.isEmpty()) {
                log.info("Category not found");
                return null;
            }

            log.info("Category found");

            return CategoryDto.builder()
                    .id(optionalCategoryDao.get().getId())
                    .categoryName(optionalCategoryDao.get().getCategoryName())
                    .build();

        } catch (Exception e) {
            log.error("An error occurred in finding a Category by id. Error: {}", e.getMessage());
            throw e;
        }
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
    public CategoryDao updateCategoryById(CategoryDto categoryDto) {
        try {
            log.info("Updating a category by id");
            Optional<CategoryDao> optionalCategoryDao = categoryRepository.findById(categoryDto.getId());

            if (optionalCategoryDao.isEmpty()) {
                log.info("Category not found");
                return null;
            }

            log.info("Category found");

            CategoryDao categoryDao = optionalCategoryDao.get();
            categoryDao.setCategoryName(categoryDto.getCategoryName());
            return categoryRepository.save(categoryDao);

        } catch (Exception e) {
            log.error("An error occurred in updating category by id. Error {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteCategoryById(Long id) {
        try {
            log.info("Deleting category by id");
            Optional<CategoryDao> optionalCategoryDao = categoryRepository.findById(id);

            if (optionalCategoryDao.isEmpty()) {
                log.info("Category not found");
            }

            log.info("Category found");
            categoryRepository.delete(optionalCategoryDao.get());

        } catch (Exception e) {
            log.error("An error occurred in deleting category by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<CategoryDao> caategoryPage(Integer pageNumber) {
        try {
            log.info("Showing categories pagination");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX);
            return categoryRepository.pageableCategory(pageable);
        } catch (Exception e) {
            log.error("An error occurred in showing categories. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<CategoryDao> searchCategory(Integer pageNumber, String keyword) {
        try {
            log.info("Searching a category");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX);
            Page<CategoryDao> categoryDaoPage = categoryRepository.searchCategoryDaoByCategoryName(keyword, pageable);
            log.info("search result c: " + categoryDaoPage);
            return categoryDaoPage;
        } catch (Exception e) {
            log.error("An error occurred in searching for categories. Error {}", e.getMessage());
            throw e;
        }
    }
}
