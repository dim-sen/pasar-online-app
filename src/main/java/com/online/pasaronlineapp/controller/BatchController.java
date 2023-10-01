package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dao.BatchDao;
import com.online.pasaronlineapp.domain.dto.BatchDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.service.impl.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Slf4j
@Controller
public class BatchController {

    @Autowired
    private BatchServiceImpl batchService;

    @GetMapping(value = "/batches/{page}")
    public String batchPage(@PathVariable(value = "page") Integer pageNUmber,
                            Model model,
                            Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<BatchDto> batchDtoPage = batchService.batchPage(pageNUmber);
        model.addAttribute("title", "Batch");
        model.addAttribute("size", batchDtoPage.getSize());
        model.addAttribute("totalPages", batchDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNUmber);
        model.addAttribute("batches", batchDtoPage);
        model.addAttribute("batchDto", new BatchDto());
        return "batches";
    }

    @GetMapping(value = "/search-batch/{page}")
    public String searchBatchPage(@RequestParam(value = "keyword") String keyword,
                                  @PathVariable(value = "page") Integer pageNumber,
                                  Model model,
                                  Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<BatchDto> batchDtoPage = batchService.searchBatch(keyword, pageNumber);
        model.addAttribute("title", "Search");
        model.addAttribute("size", batchDtoPage.getSize());
        model.addAttribute("totalPages", batchDtoPage.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("batches", batchDtoPage);
        model.addAttribute("batchDto", new BatchDto());
        return "batches";
    }

    @PostMapping(value = "/save-batch")
    public String saveBatch(@ModelAttribute(value = "batchDto") BatchDto batchDto,
                            RedirectAttributes redirectAttributes){
        try {
            batchService.createBatch(batchDto);
            redirectAttributes.addFlashAttribute("SUCCESS", "Batch Successfully Added");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Batch Failed to Add");
        }
        return "redirect:/batches/0";
    }

    @RequestMapping(value = "/find-batch-by-id", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public BatchDao findBatchById(@RequestParam(value = "id") Long id) {
        return batchService.findBatchById(id);
    }

    @PostMapping(value = "/update-batch/{id}")
    public String updateBatch(@ModelAttribute(value = "batchDto") BatchDto batchDto,
                              RedirectAttributes redirectAttributes) {
        try {
            batchService.updateBatchById(batchDto);
            redirectAttributes.addFlashAttribute("SUCCESS", "Batch Successfully Updated");
        } catch (AlreadyExistException e) {
            redirectAttributes.addFlashAttribute("ALREADY_EXIST", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Batch Failed to Update");
        }
        return "redirect:/batches/0";
    }

    @RequestMapping(value = "/delete-batch/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteBatchById(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        try {
            batchService.deleteBatchById(id);
            redirectAttributes.addFlashAttribute("SUCCESS", "Batch Changed Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("FAILED", "Batch Failed to Change");
        }
        return "redirect:/batches/0";
    }
}
