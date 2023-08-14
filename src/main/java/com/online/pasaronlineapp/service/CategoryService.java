package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.CategoryDao;
import com.online.pasaronlineapp.domain.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryDao createCategory(CategoryDto categoryDto);

    CategoryDao getCategoryById(Long id);

    List<CategoryDao> getAllCategories();

    CategoryDao updateCategoryById(CategoryDto categoryDto);

    void deleteCategoryById(Long id);

}
