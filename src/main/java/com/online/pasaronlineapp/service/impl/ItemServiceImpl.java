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
            Optional<ItemDao> optionalItemDao = itemRepository.findItemName(itemDto.getItemName());

            if (optionalItemDao.isPresent()) {
                log.info("Item Already Exist");
                return null;
            }

            ItemDao itemDao = ItemDao.builder()
                    .itemName(itemDto.getItemName())
                    .itemPrice(itemDto.getItemPrice())
                    .itemWeight(itemDto.getItemWeight())
                    .itemImage(imageUploadUtil.imgUpload(itemImage))
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

            byte[] imageBytes = itemRepository.findImageById(id);
            log.info("ImageBytes: " + Arrays.toString(imageBytes));

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
                    .itemImage(imageBytes)
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

            if (optionalItemDao.isEmpty()) {
                log.info("Item not found");
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
                itemDao.setItemImage(imageUploadUtil.imgUpload(itemImage));
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
    public Page<ItemDao> itemPage(Integer pageNumber) {
        log.info("Showing items pagination");
        Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX);
        return itemRepository.pageableItem(pageable);
    }

    @Override
    public Page<ItemDao> searchItem(Integer pageNumber, String keyword) {
        log.info("Searching an item");
        Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX);
        return itemRepository.searchItemDaoByItemNameOrItemPrice(keyword, pageable);
    }
}
