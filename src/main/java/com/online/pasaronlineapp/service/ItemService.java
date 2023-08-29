package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ItemService {

    ItemDao createItem(ItemDto itemDto, MultipartFile itemImage);

    ItemDto getItemById(Long id);

    List<ItemDao> getAllItems();

    ItemDao updateItemById(ItemDto itemDto, MultipartFile itemImage);

    void deleteItemById(Long id);
}
