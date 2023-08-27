package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.CategoryDao;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import com.online.pasaronlineapp.service.impl.CategoryServiceImpl;
import com.online.pasaronlineapp.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

//@RestController
//@RequestMapping(value = "/v1/item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private CategoryServiceImpl categoryService;

//    @PostMapping(value = "")
//    public ResponseEntity<Object> createAnItem(@RequestBody ItemDto itemDto) {
//        return itemService.createItem(itemDto);
//    }

//    @GetMapping(value = "/{id}")
//    public ResponseEntity<Object> getAnItemById(@PathVariable Long id) {
//        return itemService.getItemById(id);
//    }

//    @GetMapping(value = "")
//    public ResponseEntity<Object> getAllItems() {
//        return itemService.getAllItems();
//    }

//    @PutMapping(value = "/{id}")
//    public ResponseEntity<Object> updateAnItemById(@PathVariable Long id, @RequestBody ItemDto itemDto) {
//        return itemService.updateItemById(id, itemDto);
//    }

//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Object> deleteAnItemById(@PathVariable Long id) {
//        return itemService.deleteItemById(id);
//    }

    @GetMapping(value = "/items")
    public String ManageItems(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<ItemDao> itemDaoList = itemService.getAllItems();
        model.addAttribute("items", itemDaoList);
        model.addAttribute("size", itemDaoList.size());
        model.addAttribute("title", "Item");
        model.addAttribute("itemNew", new ItemDto());
        return "items";
    }

    @GetMapping(value = "/add-item")
    public String addItem(Model model) {
        List<CategoryDao> categoryDaoList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryDaoList);
        model.addAttribute("itemNew", new ItemDto());
        return "add-items";
    }

    @PostMapping(value = "/save-item")
    public String saveItem(@ModelAttribute(value = "itemNew") ItemDto itemDto,
                           @RequestParam(value = "file") MultipartFile itemImage,
                           RedirectAttributes redirectAttributes) {
        try {
            itemService.createItem(itemDto, itemImage);
            redirectAttributes.addFlashAttribute("success", "Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("failed", "Failed");
        }
        return "redirect:/items";
    }

    @GetMapping(value = "/update-item/{id}")
    public String updateItem(@PathVariable(value = "id") Long id, Model model) {
        return "update-items";
    }
}
