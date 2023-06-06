package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dto.ItemDto;
import com.online.pasaronlineapp.service.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createAnItem(@RequestBody ItemDto itemDto) {
        return itemService.createItem(itemDto);
    }
}
