package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import com.online.pasaronlineapp.repository.ItemRepository;
import com.online.pasaronlineapp.service.ItemService;
import com.online.pasaronlineapp.util.ImageUploadUtil;
import com.online.pasaronlineapp.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ImageUploadUtil imageUploadUtil;


//    @Override
//    public ResponseEntity<Object> createItem(ItemDto itemDto) {
//        try {
//            log.info("Creating new item");
//            Optional<ItemDao> optionalItemDao =itemRepository.findItemName(itemDto.getItemName());
//
//            if (optionalItemDao.isPresent()) {
//                log.info("Item Already Exist");
//                return ResponseUtil.build(AppConstant.ResponseCode.ALREADY_EXISTS, null, HttpStatus.CONFLICT);
//            }
//
//            ItemDao itemDao = modelMapper.map(itemDto, ItemDao.class);
//            itemRepository.save(itemDao);
//
//            ItemDto dto = modelMapper.map(itemDao, ItemDto.class);
//            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("An error occurred in creating Item. Error: {}", e.getMessage());
//            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @Override
    public ItemDao createItem(ItemDto itemDto, MultipartFile itemImage) {
        try {
            log.info("Creating new item");
            Optional<ItemDao> optionalItemDao =itemRepository.findItemName(itemDto.getItemName());

            if (optionalItemDao.isPresent()) {
                log.info("Item Already Exist");
                return null;
            }

            imageUploadUtil.imgUpload(itemImage);

            ItemDao itemDao = new ItemDao();
            itemDao.setItemName(itemDto.getItemName());
            itemDao.setItemPrice(itemDto.getItemPrice());
            itemDao.setItemWeight(itemDto.getItemWeight());
            itemDao.setItemImage(Base64.getEncoder().encodeToString(itemImage.getBytes()));
            itemDao.setCategoryDao(itemDto.getCategoryDao());

//            ItemDao itemDao = ItemDao.builder()
//                    .itemName(itemDto.getItemName())
//                    .itemPrice(itemDto.getItemPrice())
//                    .itemWeight(itemDto.getItemWeight())
//                    .itemImage(Base64.getEncoder().encodeToString(itemImage.getBytes()))
//                    .categoryDao(itemDto.getCategoryDao())
//                    .build();
            return itemRepository.save(itemDao);

        } catch (Exception e) {
            log.error("An error occurred in creating Item. Error: {}", e.getMessage());
            return null;
        }
    }

//    @Override
//    public ResponseEntity<Object> getItemById(Long id) {
//        try {
//            log.info("Getting an item by id");
//            Optional<ItemDao> optionalItemDao = itemRepository.findById(id);
//
//            if (optionalItemDao.isEmpty()) {
//                log.info("Item not found");
//                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
//            }
//
//            log.info("Item found");
//            ItemDto itemDto = modelMapper.map(optionalItemDao.get(), ItemDto.class);
//            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, itemDto, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("An error occurred in getting an item by id. Error {}", e.getMessage());
//            throw e;
//        }
//    }

//    @Override
//    public ResponseEntity<Object> getAllItems() {
//        try {
//            log.info("Getting all items");
//            List<ItemDao> itemDaos;
//            List<ItemDto> itemDtos = new ArrayList<>();
//
//            itemDaos = itemRepository.findAll();
//
//            for (ItemDao itemDao : itemDaos) {
//                itemDtos.add(modelMapper.map(itemDao, ItemDto.class));
//            }
//
//            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, itemDtos, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("An error occurred in getting all items. Error {}", e.getMessage());
//            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @Override
    public List<ItemDao> getAllItems() {
        try {
            log.info("Getting all items");
            return itemRepository.findAll();
        } catch (Exception e) {
            log.error("An error occurred in getting all items. Error {}", e.getMessage());
            return Collections.emptyList();
        }
    }


//    @Override
//    public ResponseEntity<Object> updateItemById(Long id, ItemDto itemDto) {
//        try {
//            log.info("Updating an item by id");
//            Optional<ItemDao> optionalItemDao = itemRepository.findById(id);
//
//            if (optionalItemDao.isEmpty()) {
//                log.info("Item not found");
//                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
//            }
//
//            Optional<ItemDao> itemDaoOptional = itemRepository.findItemName(itemDto.getItemName());
//
//            if (itemDaoOptional.isPresent()) {
//                log.info("Item already exist");
//                return ResponseUtil.build(AppConstant.ResponseCode.ALREADY_EXISTS, null, HttpStatus.CONFLICT);
//            }
//
//            log.info("Item found");
//            ItemDao itemDao = optionalItemDao.get();
//            itemDao.setItemName(itemDto.getItemName());
//            itemDao.setItemPrice(itemDto.getItemPrice());
//            itemDao.setItemWeight(itemDto.getItemWeight());
//            itemDao.setItemImage(itemDto.getItemImage());
//            itemRepository.save(itemDao);
//
//            ItemDto dto = modelMapper.map(itemDao, ItemDto.class);
//            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
//
//        } catch (Exception e) {
//            log.error("An error occurred in updating an item by id. Error {}", e.getMessage());
//            throw e;
//        }
//    }


    @Override
    public ItemDao updateItemById(Long id, ItemDto itemDto) {
        try {
            log.info("Updating an item by id");
            Optional<ItemDao> optionalItemDao = itemRepository.findById(id);

            if (optionalItemDao.isEmpty()) {
                log.info("Item not found");
                return null;
            }

            Optional<ItemDao> itemDaoOptional = itemRepository.findItemName(itemDto.getItemName());

            if (itemDaoOptional.isPresent()) {
                log.info("Item already exist");
                return null;
            }

            log.info("Item found");
            ItemDao itemDao = optionalItemDao.get();
            itemDao.setItemName(itemDto.getItemName());
            itemDao.setItemPrice(itemDto.getItemPrice());
            itemDao.setItemWeight(itemDto.getItemWeight());
            itemDao.setItemImage(itemDto.getItemImage());
            return itemRepository.save(itemDao);

        } catch (Exception e) {
            log.error("An error occurred in updating an item by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<Object> deleteItemById(Long id) {
        try {
            log.info("Deleting an item by id");
            Optional<ItemDao> optionalItemDao = itemRepository.findById(id);

            if (optionalItemDao.isEmpty()) {
                log.info("Item not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Item found");
            itemRepository.delete(optionalItemDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting an item by id. Error {}", e.getMessage());
            throw e;
        }
    }
}
