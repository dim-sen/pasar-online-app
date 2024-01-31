package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.WarehouseBatchDao;
import com.online.pasaronlineapp.domain.dto.BatchDto;
import com.online.pasaronlineapp.domain.dto.WarehouseBatchDto;
import com.online.pasaronlineapp.domain.dto.WarehouseDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.exception.DataNotFoundException;
import com.online.pasaronlineapp.exception.InactiveException;
import com.online.pasaronlineapp.service.impl.BatchServiceImpl;
import com.online.pasaronlineapp.service.impl.WarehouseBatchServiceImpl;
import com.online.pasaronlineapp.service.impl.WarehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class WarehouseBatchController {

    @Autowired
    private WarehouseBatchServiceImpl warehouseBatchService;

    @Autowired
    private WarehouseServiceImpl warehouseService;

    @Autowired
    private BatchServiceImpl batchService;

    @GetMapping(value = "/warehouse-batch/{page}")
    public String warehouseBatchPage(@PathVariable(value = "page") Integer pageNumber, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<WarehouseBatchDto> warehouseBatchDtoPage = warehouseBatchService.warehouseBatchPage(pageNumber);
        List<WarehouseDto> warehouseDtoList = warehouseService.getAllWarehouses();
        List<BatchDto> batchDtoList = batchService.getAllBatches();
        model.addAttribute("title", "Warehouse Batch");
        model.addAttribute("totalPages", warehouseBatchDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("warehouseBatch", warehouseBatchDtoPage);
        model.addAttribute("warehouseBatchDto", new WarehouseBatchDto());
        model.addAttribute("warehouses", warehouseDtoList);
        model.addAttribute("batches", batchDtoList);
        return "warehouse-batch";
    }

    @GetMapping(value = "/search-warehouse-batch/{page}")
    public String searchWarehouseBatch(@RequestParam(value = "keyword") String keyword,
                                       @PathVariable(value = "page") Integer pageNumber,
                                       Model model,
                                       Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<WarehouseBatchDto> warehouseBatchDtoPage = warehouseBatchService.searchWarehouseBatch(keyword, pageNumber);
        List<WarehouseDto> warehouseDtoList = warehouseService.getAllWarehouses();
        List<BatchDto> batchDtoList = batchService.getAllBatches();
        model.addAttribute("title", "Search");
        model.addAttribute("totalPages", warehouseBatchDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("warehouseBatch", warehouseBatchDtoPage);
        model.addAttribute("warehouseBatchDto", new WarehouseBatchDto());
        model.addAttribute("warehouses", warehouseDtoList);
        model.addAttribute("batches", batchDtoList);
        return "warehouse-batch";
    }

    @PostMapping(value = "/save-warehouse-batch")
    public String saveWarehouseBatch(@ModelAttribute(value = "warehouseBatchDto") WarehouseBatchDto warehouseBatchDto,
                                     RedirectAttributes redirectAttributes) {
        try {
            warehouseBatchService.createWarehouseBatch(warehouseBatchDto);
            redirectAttributes.addFlashAttribute("SUCCESS", "Warehouse Batch Successfully Added");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Warehouse Batch Failed to Add");
        }
        return "redirect:/warehouse-batch/0";
    }

    @RequestMapping(value = "/find-wb-by-id", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public WarehouseBatchDao findWarehouseBatchById(@RequestParam(value = "id") Long id) {
        return warehouseBatchService.findWarehouseBatchById(id);
    }

    @PostMapping(value = "/update-warehouse-batch/{id}")
    public String updateWarehouseBatch(@ModelAttribute(value = "warehouseBatchDto") WarehouseBatchDto warehouseBatchDto,
                                       RedirectAttributes redirectAttributes) {
        try {
            warehouseBatchService.updateWarehouseBatch(warehouseBatchDto);
            redirectAttributes.addFlashAttribute("SUCCESS", "Warehouse Batch Successfully Updated");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Warehouse Batch Failed to Update");
        }
        return "redirect:/warehouse-batch/0";
    }
}
