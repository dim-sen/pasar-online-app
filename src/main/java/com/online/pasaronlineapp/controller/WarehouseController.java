package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.WarehouseDao;
import com.online.pasaronlineapp.domain.dto.WarehouseDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.service.impl.WarehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class WarehouseController {

    @Autowired
    private WarehouseServiceImpl warehouseService;

    @GetMapping(value = "/warehouses/{page}")
    public String warehousePage(@PathVariable(value = "page") Integer pageNumber,
                                Model model,
                                Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<WarehouseDto> warehouseDtoPage = warehouseService.warehousePage(pageNumber);
        model.addAttribute("title", "Warehouse");
        model.addAttribute("totalPages", warehouseDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("warehouses", warehouseDtoPage);
        model.addAttribute("warehouseDto", new WarehouseDto());
        return "warehouses";
    }

    @GetMapping(value = "/search-warehouse/{page}")
    public String searchWarehouse(@RequestParam(value = "keyword") String keyword,
                                  @PathVariable(value = "page") Integer pageNumber,
                                  Model model,
                                  Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<WarehouseDto> warehouseDtoPage = warehouseService.searchWarehouse(keyword, pageNumber);
        model.addAttribute("title", "Search");
        model.addAttribute("totalPages", warehouseDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("warehouses", warehouseDtoPage);
        model.addAttribute("warehouseDto", new WarehouseDto());
        return "warehouses";
    }

    @PostMapping(value = "/save-warehouse")
    public String saveWarehouse(@ModelAttribute(value = "warehouseDto") WarehouseDto warehouseDto,
                                RedirectAttributes redirectAttributes) {
        try {
            warehouseService.createWarehouse(warehouseDto);
            redirectAttributes.addFlashAttribute("SUCCESS", "Warehouse Successfully Added");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Warehouse Failed to Add");
        }
        return "redirect:/warehouses/0";
    }

    @RequestMapping(value = "/find-warehouse-by-id", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public WarehouseDao findWarehouseById(@RequestParam(value = "id") Long id) {
        return warehouseService.findWarehouseById(id);
    }

    @PostMapping(value = "/update-warehouse/{id}")
    public String updateWarehouse(@ModelAttribute(value = "warehouseDto") WarehouseDto warehouseDto,
                                  RedirectAttributes redirectAttributes) {
        try {
            warehouseService.updateWarehouse(warehouseDto);
            redirectAttributes.addFlashAttribute("SUCCESS", "Warehouse Successfully Updated");
        }catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Warehouse Failed to Update");
        }
        return "redirect:/warehouses/0";
    }

    @RequestMapping(value = "/inactive-warehouse/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String inactiveWarehouseById(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        try {
            warehouseService.inactivateWarehouseById(id);
            redirectAttributes.addFlashAttribute("SUCCESS", "Warehouse Changed Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("Failed", "Warehouse Failed to Change");
        }
        return "redirect:/warehouses/0";
    }
}
