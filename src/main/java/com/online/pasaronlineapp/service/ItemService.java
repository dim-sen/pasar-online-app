package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.BarangDao;
import com.online.pasaronlineapp.domain.dto.BarangDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ItemService {

    void createItem(BarangDto itemDto, MultipartFile itemImage);

    BarangDao findItemById(Long id);

    List<BarangDto> getAllItems();

    void updateItem(BarangDto itemDto, MultipartFile itemImage);

    Page<BarangDto> itemPage(Integer pageNumber);

    Page<BarangDto> searchItem(String keyword, Integer pageNumber);
}
