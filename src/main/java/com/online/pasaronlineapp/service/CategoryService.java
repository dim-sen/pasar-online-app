package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.CategoryDao;
import com.online.pasaronlineapp.domain.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    void createCategory(CategoryDto categoryDto);

    CategoryDao findCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    void updateCategoryById(CategoryDto categoryDto);

    void deleteCategoryById(Long id);

    Page<CategoryDto> categoryPage(Integer pageNumber);

    Page<CategoryDto> searchCategory(String keyword, Integer pageNumber);
}
