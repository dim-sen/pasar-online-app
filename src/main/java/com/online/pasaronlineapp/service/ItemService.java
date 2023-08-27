package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ItemService {

//    ResponseEntity<Object> createItem(ItemDto itemDto);
    ItemDao createItem(ItemDto itemDto, MultipartFile itemImage);

//    ResponseEntity<Object> getItemById(Long id);

//    ResponseEntity<Object> getAllItems();
    List<ItemDao> getAllItems();

//    ResponseEntity<Object> updateItemById(Long id, ItemDto itemDto);
    ItemDao updateItemById(Long id, ItemDto itemDto);

    ResponseEntity<Object> deleteItemById(Long id);
}
