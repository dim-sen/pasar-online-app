package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dto.ItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {

    public ResponseEntity<Object> createItem(ItemDto itemDto);
}
