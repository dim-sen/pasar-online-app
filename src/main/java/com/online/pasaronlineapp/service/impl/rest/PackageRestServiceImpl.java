package com.online.pasaronlineapp.service.impl.rest;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.PackageDao;
import com.online.pasaronlineapp.domain.dto.PackageDto;
import com.online.pasaronlineapp.repository.PackageRepository;
import com.online.pasaronlineapp.service.rest.PackageRestService;
import com.online.pasaronlineapp.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class PackageRestServiceImpl implements PackageRestService {

    @Autowired
    private PackageRepository packageRepository;

    @Override
    public ResponseEntity<Object> getAllPackages() {
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
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, packageDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all packages. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
