package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.CategoryDao;
import com.online.pasaronlineapp.domain.dto.CategoryDto;
import com.online.pasaronlineapp.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping(value = "/categories/{page}")
    public String categoryPage(@PathVariable(value = "page") Integer pageNumber,
                               Model model,
                               Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<CategoryDao> categoryDaoPage = categoryService.caategoryPage(pageNumber);
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        model.addAttribute("title", "Category");
        model.addAttribute("size", categoryDtoList.size());
        model.addAttribute("totalPages", categoryDaoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("categories", categoryDaoPage);
        return "categories";
    }

    @GetMapping(value = "/search-category/{page}")
    public String searchCategoryPage(@PathVariable(value = "page") Integer pageNumber,
                                     @RequestParam(value = "keyword") String keyword,
                                     Model model,
                                     Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<CategoryDao> categoryDaoPage = categoryService.searchCategory(pageNumber, keyword);
        model.addAttribute("title", "Search");
        model.addAttribute("size", categoryDaoPage.getSize());
        model.addAttribute("totalPages", categoryDaoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("categories", categoryDaoPage);
        return "categories";
    }

    @GetMapping(value = "/add-category")
    public String addCategory(Model model) {
        model.addAttribute("categoryDto", new CategoryDto());
        return "add-categories";
    }

    @PostMapping(value = "/save-category")
    public String saveCategory(@ModelAttribute(value = "categoryDto") CategoryDto categoryDto,
                               RedirectAttributes redirectAttributes) {
        try {
            categoryService.createCategory(categoryDto);
            redirectAttributes.addFlashAttribute("success", "Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("failed", "Failed");
        }
        return "redirect:/categories/0";
    }

    @GetMapping(value = "/update-category/{id}")
    public String updateCategory(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Update Category");
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        model.addAttribute("categoryDto", categoryDto);
        return "update-categories";
    }

    @PostMapping(value = "/update-category/{id}")
    public String doingUpdateCategory(@ModelAttribute(value = "categoryDto") CategoryDto categoryDto,
                                      RedirectAttributes redirectAttributes) {
        try {
            categoryService.updateCategoryById(categoryDto);
            redirectAttributes.addFlashAttribute("success", "Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed");
        }
        return "redirect:/categories/0";
    }

    @RequestMapping(value = "/delete-category/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteCategoryById(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategoryById(id);
            redirectAttributes.addFlashAttribute("success", "Delete Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("failed", "Failed");
        }

        return "redirect:/categories/0";
    }

}
