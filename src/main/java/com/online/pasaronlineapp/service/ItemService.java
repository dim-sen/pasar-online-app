package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ItemService {

    void createItem(ItemDto itemDto, MultipartFile itemImage);

    ItemDao findItemById(Long id);

    void updateItemById(ItemDto itemDto, MultipartFile itemImage);

    void deleteItemById(Long id);

    Page<ItemDto> itemPage(Integer pageNumber);

    Page<ItemDto> searchItem(String keyword, Integer pageNumber);
}
