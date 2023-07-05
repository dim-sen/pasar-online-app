package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dto.ItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {

    ResponseEntity<Object> createItem(ItemDto itemDto);

    ResponseEntity<Object> getItemById(Long id);

    ResponseEntity<Object> getAllItems();

    ResponseEntity<Object> updateItemById(Long id, ItemDto itemDto);

    ResponseEntity<Object> deleteItemById(Long id);
}
