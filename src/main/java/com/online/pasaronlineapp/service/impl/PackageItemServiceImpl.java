package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dao.PackageItemDao;
import com.online.pasaronlineapp.domain.dto.PackageItemDto;
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

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void createPackageItem(PackageItemDto packageItemDto) {
        try {
            log.info("Creating new Package-Item");
            List<ItemDao> packageItemDaoList = packageItemDto.getItemDaoList();

            for (ItemDao itemDao : packageItemDaoList) {
                log.info("Find by package id and item id");
                Optional<PackageItemDao> optionalPackageItemDao = packageItemRepository.findByPackagesIdAndItemId(
                        packageItemDto.getPackageDao().getId(),
                        itemDao.getId()
                );

                if (optionalPackageItemDao.isPresent()) {
                    log.info("Package-Item already exist");
                    throw new AlreadyExistException("Package-Item Already Exist");
                }

                log.info("Found it");
                PackageItemDao packageItemDao = new PackageItemDao();
                packageItemDao.setPackageDao(packageItemDto.getPackageDao());
                packageItemDao.setItemDao(itemDao);

                packageItemRepository.save(packageItemDao);
            }
        } catch (Exception e) {
            log.error("An error occurred in creating Package-Item. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public PackageItemDao findPackageItemById(Long id) {
        try {
            log.info("Finding a Package-Item by id");
            Optional<PackageItemDao> optionalPackageItemDao = packageItemRepository.findById(id);

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
    public void updatePackageItem(PackageItemDto packageItemDto) {
        try {
            log.info("Updating a Package-Item by id");
            Optional<PackageItemDao> optionalPackageItemDao = packageItemRepository.findById(packageItemDto.getId());

            Optional<PackageItemDao> packageItemDaoOptional = packageItemRepository.findByPackagesIdAndItemId(
                    packageItemDto.getPackageDao().getId(),
                    packageItemDto.getItemDao().getId()
            );

            if (packageItemDaoOptional.isPresent() && !packageItemDaoOptional.get().getId().equals(packageItemDto.getId())) {
                log.info("Package-Item already exist");
                throw new AlreadyExistException("Package-Item Already Exist");
            }
            log.info("Package-Item Found");
            PackageItemDao packageItemDao = optionalPackageItemDao.get();
            packageItemDao.setPackageDao(packageItemDto.getPackageDao());
            packageItemDao.setItemDao(packageItemDto.getItemDao());
            packageItemRepository.save(packageItemDao);
        } catch (Exception e) {
            log.error("An error occurred in updating Package-Item by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void inactivePackageItemById(Long id) {
        try {
            log.info("Inactivating Package-Item by id");
            Optional<PackageItemDao> optionalPackageItemDao = packageItemRepository.findById(id);

            if (optionalPackageItemDao.isEmpty()) {
                log.info("Package-Item not Found");
                throw new DataNotFoundException("Package-Item not Found");
            }

            log.info("Package-Item Found");
            if (packageRepository.checkIfIsActiveFalse(optionalPackageItemDao.get().getPackageDao().getId()) ||
                    itemRepository.checkIfIsActiveFalse(optionalPackageItemDao.get().getItemDao().getId())) {
                throw new InactiveException("Failed to Change. Check Whether Package or Item is Inactive");
            } else {
                packageItemRepository.updateIsActive(id, !optionalPackageItemDao.get().isActive(), LocalDateTime.now());
            }
        } catch (Exception e) {
            log.error("An error occurred in inactivating Package-Item by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<PackageItemDto> packageItemPage(Integer pageNumber) {
        try {
            log.info("Showing Package-Item pagination");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<PackageItemDao> packageItemDaoPage = packageItemRepository.pageablePackageItem(pageable);

            return packageItemDaoPage.<PackageItemDto>map(packageItemDao -> PackageItemDto.builder()
                    .id(packageItemDao.getId())
                    .packageDao(packageItemDao.getPackageDao())
                    .itemDao(packageItemDao.getItemDao())
                    .isActive(packageItemDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in showing pagination. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<PackageItemDto> searchPackageItem(String keyword, Integer pageNumber) {
        try {
            log.info("Searching for Package-Item");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<PackageItemDao> packageItemDaoPage = packageItemRepository.searchPackageItemDaoBy(keyword.toLowerCase(), pageable);

            return packageItemDaoPage.<PackageItemDto>map(packageItemDao -> PackageItemDto.builder()
                    .id(packageItemDao.getId())
                    .packageDao(packageItemDao.getPackageDao())
                    .itemDao(packageItemDao.getItemDao())
                    .isActive(packageItemDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in searching for Package-Item. Error {}", e.getMessage());
            throw e;
        }
    }
}
