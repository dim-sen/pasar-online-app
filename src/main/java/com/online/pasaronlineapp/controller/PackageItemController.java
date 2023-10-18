package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.PackageItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import com.online.pasaronlineapp.domain.dto.PackageDto;
import com.online.pasaronlineapp.domain.dto.PackageItemDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.exception.DataNotFoundException;
import com.online.pasaronlineapp.exception.InactiveException;
import com.online.pasaronlineapp.service.impl.ItemServiceImpl;
import com.online.pasaronlineapp.service.impl.PackageItemServiceImpl;
import com.online.pasaronlineapp.service.impl.PackageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class PackageItemController {

    @Autowired
    private PackageItemServiceImpl packageItemService;

    @Autowired
    private PackageServiceImpl packageService;

    @Autowired
    private ItemServiceImpl itemService;

    @GetMapping(value = "/package-item/{page}")
    public String packageItemPage(@PathVariable(value = "page") Integer pageNumber, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<PackageItemDto> packageItemDtoPage = packageItemService.packageItemPage(pageNumber);
        List<PackageDto> packageDtoList = packageService.getAllPackages();
        List<ItemDto> itemDtoList = itemService.getAllItems();
        model.addAttribute("title", "Package-Item");
        model.addAttribute("totalPages", packageItemDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("packageItem", packageItemDtoPage);
        model.addAttribute("packageItemDto", new PackageItemDto());
        model.addAttribute("packages", packageDtoList);
        model.addAttribute("items", itemDtoList);
        return "package-item";
    }

    @GetMapping(value = "/search-package-item/{page}")
    public String searchPackageItem(@RequestParam(value = "keyword") String keyword,
                                    @PathVariable(value = "page") Integer pageNumber,
                                    Model model,
                                    Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<PackageItemDto> packageItemDtoPage = packageItemService.searchPackageItem(keyword, pageNumber);
        List<PackageDto> packageDtoList = packageService.getAllPackages();
        List<ItemDto> itemDtoList = itemService.getAllItems();
        model.addAttribute("title", "Search");
        model.addAttribute("totalPages", packageItemDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("packageItem", packageItemDtoPage);
        model.addAttribute("packageItemDto", new PackageItemDto());
        model.addAttribute("packages", packageDtoList);
        model.addAttribute("items", itemDtoList);
        return "package-item";
    }

    @PostMapping(value = "/save-package-item")
    public String savePackageItem(@ModelAttribute(value = "packageItemDto") PackageItemDto packageItemDto,
                                  RedirectAttributes redirectAttributes) {
        try {
            packageItemService.createPackageItem(packageItemDto);
            redirectAttributes.addFlashAttribute("SUCCESS", "Package Item Successfully Added");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Package Item Failed to Add");
        }
        return "redirect:/package-item/0";
    }

    @RequestMapping(value = "/find-pi-by-id", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public PackageItemDao findPackageItemById(@RequestParam(value = "id") Long id) {
        return packageItemService.findPackageItemById(id);
    }

    @PostMapping(value = "/update-package-item/{id}")
    public String updatePackageItem(@ModelAttribute(value = "packageItemDto") PackageItemDto packageItemDto,
                                    RedirectAttributes redirectAttributes) {
        try {
            packageItemService.updatePackageItem(packageItemDto);
            redirectAttributes.addFlashAttribute("SUCCESS", "Package Item Successfully Updated");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Package Item Failed to Update");
        }
        return "redirect:/package-item/0";
    }

    @RequestMapping(value = "/inactive-package-item/{id}")
    public String inactivePackageItemById(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        try {
            packageItemService.inactivePackageItemById(id);
            redirectAttributes.addFlashAttribute("SUCCESS", "Package Item Changed Successfully");
        } catch (DataNotFoundException e) {
            redirectAttributes.addFlashAttribute("NOT_FOUND", e.getMessage());
        } catch (InactiveException e) {
            redirectAttributes.addFlashAttribute("INACTIVE", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Package Item Failed to Change");
        }
        return "redirect:/package-item/0";
    }
}