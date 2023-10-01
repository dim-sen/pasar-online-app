package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.CategoryDao;
import com.online.pasaronlineapp.domain.dto.CategoryDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

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

        Page<CategoryDto> categoryDtoPage = categoryService.categoryPage(pageNumber);
        model.addAttribute("title", "Category");
        model.addAttribute("size", categoryDtoPage.getSize());
        model.addAttribute("totalPages", categoryDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("categories", categoryDtoPage);
        model.addAttribute("categoryDto", new CategoryDto());
        return "categories";
    }

    @GetMapping(value = "/search-category/{page}")
    public String searchCategoryPage(@RequestParam(value = "keyword") String keyword,
                                     @PathVariable(value = "page") Integer pageNumber,
                                     Model model,
                                     Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<CategoryDto> categoryDtoPage = categoryService.searchCategory(keyword, pageNumber);
        model.addAttribute("title", "Search");
        model.addAttribute("size", categoryDtoPage.getSize());
        model.addAttribute("totalPages", categoryDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("categories", categoryDtoPage);
        model.addAttribute("categoryDto", new CategoryDto());
        return "categories";
    }

    @PostMapping(value = "/save-category")
    public String saveCategory(@ModelAttribute(value = "categoryDto") CategoryDto categoryDto,
                               RedirectAttributes redirectAttributes) {
        try {
            categoryService.createCategory(categoryDto);
            redirectAttributes.addFlashAttribute("SUCCESS", "Category Successfully Added");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Category Failed to Add");
        }
        return "redirect:/categories/0";
    }

    @RequestMapping(value = "/find-category-by-id", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public CategoryDao findCategoryById(@RequestParam(value = "id") Long id) {
        return categoryService.findCategoryById(id);
    }

    @PostMapping(value = "/update-category/{id}")
    public String updateCategory(@ModelAttribute(value = "categoryDto") CategoryDto categoryDto,
                                      RedirectAttributes redirectAttributes) {
        try {
            categoryService.updateCategoryById(categoryDto);
            redirectAttributes.addFlashAttribute("SUCCESS", "Category Successfully Updated");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Category Failed to Update");
        }
        return "redirect:/categories/0";
    }

    @RequestMapping(value = "/delete-category/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteCategoryById(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategoryById(id);
            redirectAttributes.addFlashAttribute("SUCCESS", "Category Changed Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Category Failed to Change");
        }
        return "redirect:/categories/0";
    }

}
