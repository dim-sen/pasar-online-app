package com.online.pasaronlineapp.service.impl.rest;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.BarangDao;
import com.online.pasaronlineapp.domain.dto.BarangDto;
import com.online.pasaronlineapp.repository.ItemRepository;
import com.online.pasaronlineapp.service.rest.ItemRestService;
import com.online.pasaronlineapp.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemRestServiceImpl implements ItemRestService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public ResponseEntity<Object> getItemById(Long id) {
        try {
            log.info("Getting an item by id");
            Optional<BarangDao> optionalItemDao = itemRepository.findById(id);

            if (optionalItemDao.isEmpty()) {
                log.info("Item not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            String itemImageString = Base64.getEncoder().encodeToString(optionalItemDao.get().getBarangImage());

            log.info("Item found");
            BarangDto itemDto = BarangDto.builder()
                    .id(optionalItemDao.get().getId())
                    .barangName(optionalItemDao.get().getBarangName())
                    .barangPrice(optionalItemDao.get().getBarangPrice())
                    .barangWeight(optionalItemDao.get().getBarangWeight())
                    .barangStock(optionalItemDao.get().getBarangStock())
                    .barangDescription(optionalItemDao.get().getBarangDescription())
                    .barangImage(itemImageString)
                    .categoryDao(optionalItemDao.get().getCategoryDao())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, itemDto, HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occurred in getting an item by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<Object> getAllItems() {
        try {
            log.info("Getting all items");
            List<BarangDao> itemDaoList = itemRepository.findAll();
            List<BarangDto> itemDtoList = new ArrayList<>();

            String itemImageString;

            for (BarangDao itemDao : itemDaoList) {
               if (itemDao.isActive()) {
                   itemImageString = Base64.getEncoder().encodeToString(itemDao.getBarangImage());
                   itemDtoList.add(BarangDto.builder()
                           .id(itemDao.getId())
                           .barangName(itemDao.getBarangName())
                           .barangPrice(itemDao.getBarangPrice())
                           .barangWeight(itemDao.getBarangWeight())
                           .barangStock(itemDao.getBarangStock())
                           .barangDescription(itemDao.getBarangDescription())
                           .barangImage(itemImageString)
                           .categoryDao(itemDao.getCategoryDao())
                           .build());
               }
            }
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, itemDtoList, HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occurred in getting all items. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
