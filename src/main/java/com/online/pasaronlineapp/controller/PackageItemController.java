package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.PackageBarangDao;
import com.online.pasaronlineapp.domain.dto.BarangDto;
import com.online.pasaronlineapp.domain.dto.PackageBarangDto;
import com.online.pasaronlineapp.domain.dto.PackageDto;
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

        Page<PackageBarangDto> packageItemDtoPage = packageItemService.packageItemPage(pageNumber);
        List<PackageDto> packageDtoList = packageService.getAllPackages();
        List<BarangDto> itemDtoList = itemService.getAllItems();
        model.addAttribute("title", "Package-Item");
        model.addAttribute("totalPages", packageItemDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("packageItem", packageItemDtoPage);
        model.addAttribute("packageItemDto", new PackageBarangDto());
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

        Page<PackageBarangDto> packageItemDtoPage = packageItemService.searchPackageItem(keyword, pageNumber);
        List<PackageDto> packageDtoList = packageService.getAllPackages();
        List<BarangDto> itemDtoList = itemService.getAllItems();
        model.addAttribute("title", "Search");
        model.addAttribute("totalPages", packageItemDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("packageItem", packageItemDtoPage);
        model.addAttribute("packageItemDto", new PackageBarangDto());
        model.addAttribute("packages", packageDtoList);
        model.addAttribute("items", itemDtoList);
        return "package-item";
    }

    @PostMapping(value = "/save-package-item")
    public String savePackageItem(@ModelAttribute(value = "packageItemDto") PackageBarangDto packageItemDto,
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
    public PackageBarangDao findPackageItemById(@RequestParam(value = "id") Long id) {
        return packageItemService.findPackageItemById(id);
    }

    @PostMapping(value = "/update-package-item/{id}")
    public String updatePackageItem(@ModelAttribute(value = "packageItemDto") PackageBarangDto packageBarangDto,
                                    RedirectAttributes redirectAttributes) {
        try {
            packageItemService.updatePackageItem(packageBarangDto);
            redirectAttributes.addFlashAttribute("SUCCESS", "Package Item Successfully Updated");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Package Item Failed to Update");
        }
        return "redirect:/package-item/0";
    }
}
