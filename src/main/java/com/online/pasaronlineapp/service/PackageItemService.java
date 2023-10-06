package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.PackageItemDao;
import com.online.pasaronlineapp.domain.dto.PackageItemDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PackageItemService {

    void createPackageItem(PackageItemDto packageItemDto);

    PackageItemDao findPackageItemById(Long id);

    void updatePackageItem(PackageItemDto packageItemDto);

    void inactivePackageItemById(Long id);

    Page<PackageItemDto> packageItemPage(Integer pageNumber);

    Page<PackageItemDto> searchPackageItem(String keyword, Integer pageNumber);
}
