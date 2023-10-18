package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.service.impl.CategoryRestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/category")
public class CategoryRestController {

    @Autowired
    private CategoryRestServiceImpl categoryRestService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllCategories() {
        return categoryRestService.getAllCategories();
    }
}
