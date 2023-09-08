package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.CategoryDto;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import com.online.pasaronlineapp.service.impl.CategoryServiceImpl;
import com.online.pasaronlineapp.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        Page<ItemDao> itemDaoPage = itemService.itemPage(pageNumber);
        List<ItemDto> itemDtoList = itemService.getAllItems();
        model.addAttribute("title", "Item");
        model.addAttribute("size", itemDtoList.size());
        model.addAttribute("totalPages", itemDaoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("items", itemDaoPage);
        return "items";
    }

    @GetMapping(value = "/search-item/{page}")
    public String searchItemPage(@PathVariable(value = "page") Integer pageNumber,
                                 @RequestParam(value = "keyword") String keyword,
                                 Model model,
                                 Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ItemDao> itemDaoPage = itemService.searchItem(pageNumber, keyword);
        model.addAttribute("title", "Search");
        model.addAttribute("items", itemDaoPage);
        model.addAttribute("size", itemDaoPage.getSize());
        model.addAttribute("totalPages", itemDaoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        return "result-items";
    }

    @GetMapping(value = "/add-item")
    public String addItem(Model model) {
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("itemDto", new ItemDto());
        return "add-items";
    }

    @PostMapping(value = "/save-item")
    public String saveItem(@ModelAttribute(value = "itemDto") ItemDto itemDto,
                           @RequestParam(value = "file") MultipartFile itemImage,
                           RedirectAttributes redirectAttributes) {
        try {
            itemService.createItem(itemDto, itemImage);
            redirectAttributes.addFlashAttribute(AppConstant.FlashAttribute.SAVE_SUCCESS);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(AppConstant.FlashAttribute.SAVE_FAILED);
        }
        return "redirect:/items/0";
    }

    @GetMapping(value = "/update-item/{id}")
    public String updateItemById(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        model.addAttribute("title", "Update Item");
        model.addAttribute("categories", categoryDtoList);
        ItemDto itemDto = itemService.getItemById(id);
        model.addAttribute("itemDto", itemDto);
        return "update-items";
    }

    @PostMapping(value = "/update-item/{id}")
    public String doingUpdateItem(
            @ModelAttribute(value = "itemDto") ItemDto itemDto,
            @RequestParam(value = "file") MultipartFile itemImage,
            RedirectAttributes redirectAttributes) {

        try {
            itemService.updateItemById(itemDto, itemImage);
            redirectAttributes.addFlashAttribute(AppConstant.FlashAttribute.UPDATE_SUCCESS);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(AppConstant.FlashAttribute.UPDATE_FAILED);
        }

        return "redirect:/items/0";
    }

    @RequestMapping(value = "/delete-item/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteItemByid(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        try {
            itemService.deleteItemById(id);
            redirectAttributes.addFlashAttribute(AppConstant.FlashAttribute.DELETE_SUCCESS);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(AppConstant.FlashAttribute.DELETE_FAILED);
        }
        return "redirect:/items/0";
    }
}
