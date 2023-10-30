package com.online.pasaronlineapp.service.impl.rest;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
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
            Optional<ItemDao> optionalItemDao = itemRepository.findById(id);

            if (optionalItemDao.isEmpty()) {
                log.info("Item not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            String itemImageString = Base64.getEncoder().encodeToString(optionalItemDao.get().getItemImage());

            log.info("Item found");
            ItemDto itemDto = ItemDto.builder()
                    .id(optionalItemDao.get().getId())
                    .itemName(optionalItemDao.get().getItemName())
                    .itemPrice(optionalItemDao.get().getItemPrice())
                    .itemWeight(optionalItemDao.get().getItemWeight())
                    .itemStock(optionalItemDao.get().getItemStock())
                    .itemDescription(optionalItemDao.get().getItemDescription())
                    .itemImage(itemImageString)
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
            List<ItemDao> itemDaoList = itemRepository.findAll();
            List<ItemDto> itemDtoList = new ArrayList<>();

            String itemImageString;

            for (ItemDao itemDao : itemDaoList) {
               if (itemDao.isActive()) {
                   itemImageString = Base64.getEncoder().encodeToString(itemDao.getItemImage());
                   itemDtoList.add(ItemDto.builder()
                           .id(itemDao.getId())
                           .itemName(itemDao.getItemName())
                           .itemPrice(itemDao.getItemPrice())
                           .itemWeight(itemDao.getItemWeight())
                           .itemStock(itemDao.getItemStock())
                           .itemDescription(itemDao.getItemDescription())
                           .itemImage(itemImageString)
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
