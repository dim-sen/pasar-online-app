package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.PackageDao;
import com.online.pasaronlineapp.domain.dto.PackageDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.exception.DataNotFoundException;
import com.online.pasaronlineapp.service.impl.PackageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class PackageController {

    @Autowired
    private PackageServiceImpl packageService;

    @GetMapping(value = "/packages/{page}")
    public String packagePage(@PathVariable(value = "page") Integer pageNumber,
                              Model model,
                              Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<PackageDto> packageDtoPage = packageService.packagePage(pageNumber);
        model.addAttribute("title", "Package");
        model.addAttribute("totalPages", packageDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("packages", packageDtoPage);
        model.addAttribute("packageDto", new PackageDto());
        return "packages";
    }

    @GetMapping(value = "/search-package/{page}")
    public String searchPackage(@RequestParam(value = "keyword") String keyword,
                                @PathVariable(value = "page") Integer pageNumber,
                                Model model,
                                Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<PackageDto> packageDtoPage = packageService.searchPackage(keyword, pageNumber);
        model.addAttribute("title", "Search");
        model.addAttribute("totalPages", packageDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("packages", packageDtoPage);
        model.addAttribute("packageDto", new PackageDto());
        return "packages";
    }

    @PostMapping(value = "/save-package")
    public String savePackage(@ModelAttribute(value = "PackageDto") PackageDto packageDto,
                              @RequestParam(value = "file") MultipartFile packageItem,
                              RedirectAttributes redirectAttributes) {
        try {
            packageService.createPackage(packageDto, packageItem);
            redirectAttributes.addFlashAttribute("SUCCESS", "Package Successfully Added");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Package Failed to Add");
        }
        return "redirect:/packages/0";
    }

    @RequestMapping(value = "/find-package-by-id", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public PackageDao findPackageById(@RequestParam(value = "id") Long id) {
        return packageService.findPackageById(id);
    }

    @PostMapping(value = "/update-package/{id}")
    public String updatePackage(@ModelAttribute(value = "packageDto") PackageDto packageDto,
                                @RequestParam(value = "file") MultipartFile packageItem,
                                RedirectAttributes redirectAttributes) {
        try {
            packageService.updatePackage(packageDto, packageItem);
            redirectAttributes.addFlashAttribute("SUCCESS", "Package Successfully Updated");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Package Failed to Update");
        }
        return "redirect:/packages/0";
    }

    @RequestMapping(value = "/inactive-package/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String inactivePackageById(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        try {
            packageService.inactivePackageById(id);
            redirectAttributes.addFlashAttribute("SUCCESS", "Package Changed Successfully");
        } catch (DataNotFoundException e) {
            redirectAttributes.addFlashAttribute("NOT_FOUND", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Package Failed to Change");
        }
        return "redirect:/packages/0";
    }
}
