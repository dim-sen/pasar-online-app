package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.service.impl.ItemRestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/item")
public class ItemRestController {

    @Autowired
    private ItemRestServiceImpl itemRestService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable(value = "id") Long id) {
        return itemRestService.getItemById(id);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllItems() {
        return itemRestService.getAllItems();
    }
}
