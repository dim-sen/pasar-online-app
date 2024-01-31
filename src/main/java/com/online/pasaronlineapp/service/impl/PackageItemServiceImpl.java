package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.BarangDao;
import com.online.pasaronlineapp.domain.dao.PackageBarangDao;
import com.online.pasaronlineapp.domain.dto.PackageBarangDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.exception.DataNotFoundException;
import com.online.pasaronlineapp.exception.InactiveException;
import com.online.pasaronlineapp.repository.ItemRepository;
import com.online.pasaronlineapp.repository.PackageItemRepository;
import com.online.pasaronlineapp.repository.PackageRepository;
import com.online.pasaronlineapp.service.PackageItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PackageItemServiceImpl implements PackageItemService {

    @Autowired
    private PackageItemRepository packageItemRepository;

    @Override
    public void createPackageItem(PackageBarangDto packageItemDto) {
        try {
            log.info("Creating new Package-Item");
            List<BarangDao> packageItemDaoList = packageItemDto.getBarangDaoList();

            for (BarangDao itemDao : packageItemDaoList) {
                log.info("Find by package id and item id");
                Optional<PackageBarangDao> optionalPackageItemDao = packageItemRepository.findByPackagesIdAndItemId(
                        packageItemDto.getPackageDao().getId(),
                        itemDao.getId()
                );

                if (optionalPackageItemDao.isPresent()) {
                    log.info("Package-Item already exist");
                    throw new AlreadyExistException("Package-Item Already Exist");
                }

                log.info("Found it");
                PackageBarangDao packageItemDao = new PackageBarangDao();
                packageItemDao.setPackageDao(packageItemDto.getPackageDao());
                packageItemDao.setBarangDao(itemDao);

                packageItemRepository.save(packageItemDao);
            }
        } catch (Exception e) {
            log.error("An error occurred in creating Package-Item. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public PackageBarangDao findPackageItemById(Long id) {
        try {
            log.info("Finding a Package-Item by id");
            Optional<PackageBarangDao> optionalPackageItemDao = packageItemRepository.findById(id);

            if (optionalPackageItemDao.isEmpty()) {
                log.info("Package-Item not found");
                throw new DataNotFoundException("Package-Item not Found");
            }

            log.info("Package-Item Found");
            return optionalPackageItemDao.get();
        } catch (Exception e) {
            log.error("An error occurred in finding Package-Item. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updatePackageItem(PackageBarangDto packageBarangDto) {
        try {
            log.info("Updating a Package-Item by id");
            Optional<PackageBarangDao> optionalPackageItemDao = packageItemRepository.findById(packageBarangDto.getId());

            Optional<PackageBarangDao> packageItemDaoOptional = packageItemRepository.findByPackagesIdAndItemId(
                    packageBarangDto.getPackageDao().getId(),
                    packageBarangDto.getBarangDao().getId()
            );

            if (packageItemDaoOptional.isPresent() && !packageItemDaoOptional.get().getId().equals(packageBarangDto.getId())) {
                log.info("Package-Item already exist");
                throw new AlreadyExistException("Package-Item Already Exist");
            }
            log.info("Package-Item Found");
            PackageBarangDao packageItemDao = optionalPackageItemDao.get();
            packageItemDao.setPackageDao(packageBarangDto.getPackageDao());
            packageItemDao.setBarangDao(packageBarangDto.getBarangDao());
            packageItemDao.setActive(packageBarangDto.isActive());
            packageItemRepository.save(packageItemDao);
        } catch (Exception e) {
            log.error("An error occurred in updating Package-Item by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<PackageBarangDto> packageItemPage(Integer pageNumber) {
        try {
            log.info("Showing Package-Item pagination");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<PackageBarangDao> packageItemDaoPage = packageItemRepository.pageablePackageItem(pageable);

            return packageItemDaoPage.<PackageBarangDto>map(packageItemDao -> PackageBarangDto.builder()
                    .id(packageItemDao.getId())
                    .packageDao(packageItemDao.getPackageDao())
                    .barangDao(packageItemDao.getBarangDao())
                    .isActive(packageItemDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in showing pagination. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<PackageBarangDto> searchPackageItem(String keyword, Integer pageNumber) {
        try {
            log.info("Searching for Package-Item");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<PackageBarangDao> packageItemDaoPage = packageItemRepository.searchPackageItemDaoBy(keyword.toLowerCase(), pageable);

            return packageItemDaoPage.<PackageBarangDto>map(packageItemDao -> PackageBarangDto.builder()
                    .id(packageItemDao.getId())
                    .packageDao(packageItemDao.getPackageDao())
                    .barangDao(packageItemDao.getBarangDao())
                    .isActive(packageItemDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in searching for Package-Item. Error {}", e.getMessage());
            throw e;
        }
    }
}
