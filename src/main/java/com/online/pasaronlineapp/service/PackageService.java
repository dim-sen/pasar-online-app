package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.PackageDao;
import com.online.pasaronlineapp.domain.dto.PackageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PackageService {

    void createPackage(PackageDto packageDto, MultipartFile packageImage);

    PackageDao findPackageById(Long id);

    List<PackageDto> getAllPackages();

    void updatePackage(PackageDto packageDto, MultipartFile packageImage);

    Page<PackageDto> packagePage(Integer pageNumber);

    Page<PackageDto> searchPackage(String keyword, Integer pageNumber);
}
