package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ItemService {

    ItemDao createItem(ItemDto itemDto, MultipartFile itemImage);

    ItemDto getItemById(Long id);

    String getItemImageById(Long id);

    List<ItemDto> getAllItems();

    ItemDao updateItemById(ItemDto itemDto, MultipartFile itemImage);

    void deleteItemById(Long id);

    Page<ItemDao> itemPage(Integer pageNumber);

    Page<ItemDao> searchItem(Integer pageNumber, String keyword);
}
