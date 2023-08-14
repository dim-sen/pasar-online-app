package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.CategoryDao;
import com.online.pasaronlineapp.domain.dto.CategoryDto;
import com.online.pasaronlineapp.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping(value = "/categories")
    public String manageCategories(Model model) {
        List<CategoryDao> categoryDaoList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryDaoList);
        model.addAttribute("size", categoryDaoList.size());
        model.addAttribute("title", "Category");
        model.addAttribute("categoryNew", new CategoryDto());
        return "categories";
    }

    @PostMapping(value = "/add-category")
    public String addCategory(@ModelAttribute("categoryNew") CategoryDto categoryDto,
                              RedirectAttributes redirectAttributes) {
        try {
            categoryService.createCategory(categoryDto);
            redirectAttributes.addFlashAttribute("success", "Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public CategoryDao getACategoryById(Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping(value = "/update-category")
    public String updateCategory(CategoryDto categoryDto, RedirectAttributes redirectAttributes) {
        try {
            categoryService.updateCategoryById(categoryDto);
            redirectAttributes.addFlashAttribute("success", "Update Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Server Error");
        }

        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteCategoryById(Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategoryById(id);
            redirectAttributes.addFlashAttribute("success", "Delete Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failed", "Failed");
        }

        return "redirect:/categories";
    }

}
