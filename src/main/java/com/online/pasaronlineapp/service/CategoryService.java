package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.CategoryDao;
import com.online.pasaronlineapp.domain.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryDao createCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDao updateCategoryById(CategoryDto categoryDto);

    void deleteCategoryById(Long id);

    Page<CategoryDao> caategoryPage(Integer pageNumber);

    Page<CategoryDao> searchCategory(Integer pageNumber, String keyword);
}
