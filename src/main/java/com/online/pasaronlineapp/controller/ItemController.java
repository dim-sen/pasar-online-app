package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.BarangDao;
import com.online.pasaronlineapp.domain.dto.CategoryDto;
import com.online.pasaronlineapp.domain.dto.BarangDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.exception.DataNotFoundException;
import com.online.pasaronlineapp.service.impl.CategoryServiceImpl;
import com.online.pasaronlineapp.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping(value = "/items/{page}")
    public String itemPage(@PathVariable(value = "page") Integer pageNumber,
                           Model model,
                           Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<BarangDto> itemDtoPage = itemService.itemPage(pageNumber);
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        model.addAttribute("title", "Item");
        model.addAttribute("totalPages", itemDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("items", itemDtoPage);
        model.addAttribute("itemDto", new BarangDto());
        model.addAttribute("categories", categoryDtoList);
        return "items";
    }

    @GetMapping(value = "/search-item/{page}")
    public String searchItemPage(@RequestParam(value = "keyword") String keyword,
                                 @PathVariable(value = "page") Integer pageNumber,
                                 Model model,
                                 Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<BarangDto> itemDtoPage = itemService.searchItem(keyword, pageNumber);
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        model.addAttribute("title", "Item");
        model.addAttribute("totalPages", itemDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("items", itemDtoPage);
        model.addAttribute("itemDto", new BarangDto());
        model.addAttribute("categories", categoryDtoList);
        return "items";
    }

    @PostMapping(value = "/save-item")
    public String saveItem(@ModelAttribute(value = "itemDto") @Valid BarangDto itemDto,
                           @RequestParam(value = "file") MultipartFile itemImage,
                           RedirectAttributes redirectAttributes) {

        try {
            itemService.createItem(itemDto, itemImage);
            redirectAttributes.addFlashAttribute("SUCCESS", "Item Successfully Added");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Item Failed to Add");
        }
        return "redirect:/items/0";
    }

    @RequestMapping(value = "/find-item-by-id", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public BarangDao findItemById(@RequestParam(value = "id") Long id) {
        return itemService.findItemById(id);
    }

    @PostMapping(value = "/update-item/{id}")
    public String updateItem(@ModelAttribute(value = "itemDto") BarangDto itemDto,
            @RequestParam(value = "file") MultipartFile itemImage,
            RedirectAttributes redirectAttributes) {

        try {
            itemService.updateItem(itemDto, itemImage);
            redirectAttributes.addFlashAttribute("SUCCESS", "Item Successfully Updated");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Item Failed to Update");
        }
        return "redirect:/items/0";
    }

    @RequestMapping(value = "/inactive-item/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String inactiveItemById(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        try {
            itemService.inactivateItemById(id);
            redirectAttributes.addFlashAttribute("SUCCESS", "Item Changed Successfully");
        } catch (DataNotFoundException e) {
            redirectAttributes.addFlashAttribute("NOT_FOUND", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Item Failed to Change");
        }
        return "redirect:/items/0";
    }
}
