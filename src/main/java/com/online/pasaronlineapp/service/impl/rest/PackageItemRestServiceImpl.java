package com.online.pasaronlineapp.service.impl.rest;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.PackageItemDao;
import com.online.pasaronlineapp.domain.dto.PackageItemDto;
import com.online.pasaronlineapp.repository.PackageItemRepository;
import com.online.pasaronlineapp.service.rest.PackageItemRestService;
import com.online.pasaronlineapp.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PackageItemRestServiceImpl implements PackageItemRestService {

    @Autowired
    private PackageItemRepository packageItemRepository;

    @Override
    public ResponseEntity<Object> getAllPackageItemByPackageId(Long id) {
        try {
            log.info("Getting all Package-Item by package id");
            List<PackageItemDao> packageItemDaoList = packageItemRepository.findAllByPackageId(id);
            List<PackageItemDto> packageItemDtoList = new ArrayList<>();

            for (PackageItemDao packageItemDao : packageItemDaoList) {
                if (packageItemDao.isActive()) {
                    packageItemDtoList.add(PackageItemDto.builder()
                            .id(packageItemDao.getId())
                            .packageDao(packageItemDao.getPackageDao())
                            .itemDao(packageItemDao.getItemDao())
                            .build());
                }
            }
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, packageItemDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all Package Item by package id. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
