package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.CategoryDao;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import com.online.pasaronlineapp.service.impl.CategoryServiceImpl;
import com.online.pasaronlineapp.service.impl.ItemServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping(value = "/items")
    public String ManageItems(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<ItemDto> itemDtoList = itemService.getAllItems();
        model.addAttribute("items", itemDtoList);
        model.addAttribute("size", itemDtoList.size());
        model.addAttribute("title", "Item");
        return "items";
    }

    @GetMapping(value = "/items/{page}")
    public String itemPage(@PathVariable(value = "page") Integer pageNumber,
                           Model model,
                           Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<ItemDao> itemDaoPage = itemService.itemPage(pageNumber);
        model.addAttribute("title", "Item");
        model.addAttribute("size", itemDaoPage.getSize());
        model.addAttribute("totalPages", itemDaoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("items", itemDaoPage);
        return "items";
    }

    @GetMapping(value = "/search-result/{page}")
    public String searchItem(@PathVariable(value = "page") Integer pageNumber,
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
        List<CategoryDao> categoryDaoList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryDaoList);
        model.addAttribute("itemDto", new ItemDto());
        return "add-items";
    }

    @PostMapping(value = "/save-item")
    public String saveItem(@ModelAttribute(value = "itemDto") ItemDto itemDto,
                           @RequestParam(value = "file") MultipartFile itemImage,
                           RedirectAttributes redirectAttributes) {
        try {
            log.info("itemDto id (save): " + itemDto.getId());
            log.info("itemDto: " + itemDto);
            itemService.createItem(itemDto, itemImage);
            redirectAttributes.addFlashAttribute("success", "Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("failed", "Failed");
        }
        return "redirect:/items/0";
    }

    @GetMapping(value = "/update-item/{id}")
    public String updateItemById(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<CategoryDao> categoryDaoList = categoryService.getAllCategories();
        model.addAttribute("title", "Update Item");
        model.addAttribute("categories", categoryDaoList);
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
            redirectAttributes.addFlashAttribute("success", "Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed");
        }

        return "redirect:/items/0";
    }

    @RequestMapping(value = "/delete-item/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteItemByid(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        try {
            itemService.deleteItemById(id);
            redirectAttributes.addFlashAttribute("success", "Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed");
        }
        return "redirect:/items/0";
    }
}
