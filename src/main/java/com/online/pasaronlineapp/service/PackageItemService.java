package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.PackageBarangDao;
import com.online.pasaronlineapp.domain.dto.PackageBarangDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PackageItemService {

    void createPackageItem(PackageBarangDto packageItemDto);

    PackageBarangDao findPackageItemById(Long id);

    void updatePackageItem(PackageBarangDto packageItemDto);

    Page<PackageBarangDto> packageItemPage(Integer pageNumber);

    Page<PackageBarangDto> searchPackageItem(String keyword, Integer pageNumber);
}
