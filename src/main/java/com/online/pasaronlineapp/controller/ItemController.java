package com.online.pasaronlineapp.controller;

import com.online.pasaronlineapp.domain.dto.ItemDto;
import com.online.pasaronlineapp.service.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createAnItem(@RequestBody ItemDto itemDto) {
        return itemService.createItem(itemDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAnItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllItems() {
        return itemService.getAllItems();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAnItemById(@PathVariable Long id, @RequestBody ItemDto itemDto) {
        return itemService.updateItemById(id, itemDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAnItemById(@PathVariable Long id) {
        return itemService.deleteItemById(id);
    }
}
