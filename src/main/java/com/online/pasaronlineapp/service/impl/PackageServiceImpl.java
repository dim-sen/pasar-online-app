package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.PackageDao;
import com.online.pasaronlineapp.domain.dao.PackageBarangDao;
import com.online.pasaronlineapp.domain.dto.PackageDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.exception.DataNotFoundException;
import com.online.pasaronlineapp.repository.PackageItemRepository;
import com.online.pasaronlineapp.repository.PackageRepository;
import com.online.pasaronlineapp.service.PackageService;
import com.online.pasaronlineapp.util.ImageUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageItemRepository packageItemRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ImageUploadUtil imageUploadUtil;

    @Override
    public void createPackage(PackageDto packageDto, MultipartFile packageImage) {
        try {
            log.info("Creating new package");
            Optional<PackageDao> optionalPackageDao = packageRepository.findPackageDaoByPackageName(packageDto.getPackageName());

            if (optionalPackageDao.isPresent()) {
                log.info("Package already exist");
                throw new AlreadyExistException("Package Already Exist");
            }

            PackageDao packageDao = PackageDao.builder()
                    .packageName(packageDto.getPackageName())
                    .packagePrice(packageDto.getPackagePrice())
                    .packageWeight(packageDto.getPackageWeight())
                    .packageDescription(packageDto.getPackageDescription())
                    .packageImage(imageUploadUtil.imgUpload(packageImage))
                    .build();

            packageRepository.save(packageDao);

        } catch (Exception e) {
            log.error("An error occurred in creating package. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public PackageDao findPackageById(Long id) {
        try {
            log.info("Finding a package by id");
            Optional<PackageDao> optionalPackageDao = packageRepository.findById(id);

            if (optionalPackageDao.isEmpty()) {
                log.info("Package not found");
                throw new DataNotFoundException("Package not Found");
            }

            log.info("Package Found");
            return optionalPackageDao.get();
        } catch (Exception e) {
            log.error("An error occurred in finding a package by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PackageDto> getAllPackages() {
        try {
            log.info("Getting all packages");
            List<PackageDao> packageDaoList = packageRepository.findAll();
            List<PackageDto> packageDtoList = new ArrayList<>();

            String packageImageString;

            for (PackageDao packageDao : packageDaoList) {
                packageImageString = Base64.getEncoder().encodeToString(packageDao.getPackageImage());
                packageDtoList.add(PackageDto.builder()
                                .id(packageDao.getId())
                                .packageName(packageDao.getPackageName())
                                .packagePrice(packageDao.getPackagePrice())
                                .packageWeight(packageDao.getPackageWeight())
                                .packageDescription(packageDao.getPackageDescription())
                                .packageImage(packageImageString)
                        .build());
            }
            return packageDtoList;
        } catch (Exception e) {
            log.error("An error occurred in getting all packages. Error {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void updatePackage(PackageDto packageDto, MultipartFile packageImage) {
        try {
            log.info("Updating a package by id");
            Optional<PackageDao> optionalPackageDao = packageRepository.findById(packageDto.getId());

            Optional<PackageDao> packageDaoOptional = packageRepository.findPackageDaoByPackageName(packageDto.getPackageName());
            if (packageDaoOptional.isPresent() && !packageDaoOptional.get().getId().equals(packageDto.getId())) {
                log.info("Package already exist");
                throw new AlreadyExistException("Package Already Exist");
            }

            log.info("Package Found");
            PackageDao packageDao = optionalPackageDao.get();
            packageDao.setPackageName(packageDto.getPackageName());
            packageDao.setPackagePrice(packageDto.getPackagePrice());
            packageDao.setPackageWeight(packageDto.getPackageWeight());
            packageDao.setPackageDescription(packageDto.getPackageDescription());

            if (packageImage.isEmpty()) {
                log.info("packageImage is null");
                packageDao.setPackageImage(optionalPackageDao.get().getPackageImage());
            } else {
                packageDao.setPackageImage(imageUploadUtil.imgUpload(packageImage));
            }

            packageDao.setActive(packageDto.isActive());

            packageRepository.save(packageDao);
        } catch (Exception e) {
            log.error("An error occurred in updating a package by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<PackageDto> packagePage(Integer pageNumber) {
        try {
            log.info("Showing packages pagination");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<PackageDao> packageDaoPage = packageRepository.pageablePackage(pageable);

            return packageDaoPage.<PackageDto>map(packageDao -> PackageDto.builder()
                    .id(packageDao.getId())
                    .packageName(packageDao.getPackageName())
                    .packagePrice(packageDao.getPackagePrice())
                    .packageWeight(packageDao.getPackageWeight())
                    .packageDescription(packageDao.getPackageDescription())
                    .packageImage(Base64.getEncoder().encodeToString(packageDao.getPackageImage()))
                    .isActive(packageDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in showing packages. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<PackageDto> searchPackage(String keyword, Integer pageNumber) {
        try {
            log.info("Searching for package");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<PackageDao> packageDaoPage = packageRepository.searchPackageDaoByKeyword(keyword.toLowerCase(), pageable);

            return packageDaoPage.<PackageDto>map(packageDao -> PackageDto.builder()
                    .id(packageDao.getId())
                    .packageName(packageDao.getPackageName())
                    .packagePrice(packageDao.getPackagePrice())
                    .packageWeight(packageDao.getPackageWeight())
                    .packageDescription(packageDao.getPackageDescription())
                    .packageImage(Base64.getEncoder().encodeToString(packageDao.getPackageImage()))
                    .isActive(packageDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in searching for packages. Error {}", e.getMessage());
            throw e;
        }
    }
}
