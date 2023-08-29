package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import com.online.pasaronlineapp.repository.ItemRepository;
import com.online.pasaronlineapp.service.ItemService;
import com.online.pasaronlineapp.util.ImageUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

            ItemDao itemDao = ItemDao.builder()
                    .itemName(itemDto.getItemName())
                    .itemPrice(itemDto.getItemPrice())
                    .itemWeight(itemDto.getItemWeight())
                    .itemImage(Base64.getEncoder().encodeToString(itemImage.getBytes()))
                    .categoryDao(itemDto.getCategoryDao())
                    .build();
            return itemRepository.save(itemDao);

        } catch (Exception e) {
            log.error("An error occurred in creating Item. Error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public ItemDto getItemById(Long id) {
        try {
            log.info("Getting an item by id");
            Optional<ItemDao> optionalItemDao = itemRepository.findById(id);

            if (optionalItemDao.isEmpty()) {
                log.info("Item not found");
                return null;
            }

            log.info("Item found");

            return ItemDto.builder()
                    .id(optionalItemDao.get().getId())
                    .itemName(optionalItemDao.get().getItemName())
                    .itemPrice(optionalItemDao.get().getItemPrice())
                    .itemWeight(optionalItemDao.get().getItemWeight())
                    .itemImage(optionalItemDao.get().getItemImage())
                    .categoryDao(optionalItemDao.get().getCategoryDao())
                    .build();

        } catch (Exception e) {
            log.error("An error occurred in getting an item by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ItemDto> getAllItems() {
        try {
            log.info("Getting all items");
            List<ItemDao> itemDaoList = itemRepository.findAll();
            List<ItemDto> itemDtoList = new ArrayList<>();

            for (ItemDao itemDao : itemDaoList) {
                itemDtoList.add(ItemDto.builder()
                                .id(itemDao.getId())
                                .itemName(itemDao.getItemName())
                                .itemPrice(itemDao.getItemPrice())
                                .itemWeight(itemDao.getItemWeight())
                                .itemImage(itemDao.getItemImage())
                                .categoryDao(itemDao.getCategoryDao())
                        .build());
            }

            return itemDtoList;
        } catch (Exception e) {
            log.error("An error occurred in getting all items. Error {}", e.getMessage());
            return Collections.emptyList();
        }
    }



    @Override
    public ItemDao updateItemById(ItemDto itemDto, MultipartFile itemImage) {
        try {
            log.info("Updating an item by id");
            Optional<ItemDao> optionalItemDao = itemRepository.findById(itemDto.getId());
            log.info("itemDao: " + optionalItemDao);

            if (optionalItemDao.isEmpty()) {
                log.info("Item not found");
                return null;
            }

            Optional<ItemDao> itemDaoOptional = itemRepository.findItemName(itemDto.getItemName());
            log.info("dto name: " + itemDto.getItemName());
            log.info("dao name: " + itemDaoOptional);

            if (itemDaoOptional.isPresent()) {
                log.info("Item already exist");
                return null;
            }

            log.info("Item found");
            ItemDao itemDao = optionalItemDao.get();
            itemDao.setItemName(itemDto.getItemName());
            itemDao.setItemPrice(itemDto.getItemPrice());
            itemDao.setItemWeight(itemDto.getItemWeight());

            if (itemImage.isEmpty()) {
                log.info("itemImage is null");
                itemDao.setItemImage(optionalItemDao.get().getItemImage());
            } else {
                if (!imageUploadUtil.checkIfExist(itemImage)) {
                    log.info("itemImage isn't null & upload");
                    imageUploadUtil.imgUpload(itemImage);
                }

                itemDao.setItemImage(Base64.getEncoder().encodeToString(itemImage.getBytes()));
            }

            return itemRepository.save(itemDao);

        } catch (Exception e) {
            log.error("An error occurred in updating an item by id. Error {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteItemById(Long id) {
        try {
            log.info("Deleting an item by id");
            Optional<ItemDao> optionalItemDao = itemRepository.findById(id);

            if (optionalItemDao.isEmpty()) {
                log.info("Item not found");
            }

            log.info("Item found");
            itemRepository.delete(optionalItemDao.get());
        } catch (Exception e) {
            log.error("An error occurred in deleting an item by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<ItemDao> ITEM_DAO_PAGE(Integer number) {
        Pageable pageable = PageRequest.of(number, AppConstant.PAGE_MAX);
        return itemRepository.pageableItem(pageable);
    }
}
