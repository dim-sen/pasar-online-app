package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dao.PackageItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import com.online.pasaronlineapp.exception.AlreadyExistException;
import com.online.pasaronlineapp.exception.DataNotFoundException;
import com.online.pasaronlineapp.repository.ItemRepository;
import com.online.pasaronlineapp.repository.PackageItemRepository;
import com.online.pasaronlineapp.service.ItemService;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ImageUploadUtil imageUploadUtil;

    @Autowired
    private PackageItemRepository packageItemRepository;

    @Override
    public void createItem(ItemDto itemDto, MultipartFile itemImage) {
        try {
            log.info("Creating new item");
            Optional<ItemDao> optionalItemDao = itemRepository.findItemName(itemDto.getItemName());

            if (optionalItemDao.isPresent()) {
                log.info("Item Already Exist");
                throw new AlreadyExistException("Item Already Exist");
            }

            ItemDao itemDao = ItemDao.builder()
                    .itemName(itemDto.getItemName())
                    .itemPrice(itemDto.getItemPrice())
                    .itemWeight(itemDto.getItemWeight())
                    .itemStock(itemDto.getItemStock())
                    .itemDescription(itemDto.getItemDescription())
                    .itemImage(imageUploadUtil.imgUpload(itemImage))
                    .categoryDao(itemDto.getCategoryDao())
                    .build();
            itemRepository.save(itemDao);

        } catch (Exception e) {
            log.error("An error occurred in creating Item. Error: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ItemDao findItemById(Long id) {
        try {
            log.info("Finding an item by id");
            Optional<ItemDao> optionalItemDao = itemRepository.findById(id);

            if (optionalItemDao.isEmpty()) {
                log.info("Item not Found");
                throw new DataNotFoundException("Item not Found");
            }

            log.info("Item Found");
            return optionalItemDao.get();
        } catch (Exception e) {
            log.error("An error occurred in finding an item by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ItemDto> getAllItems() {
        try {
            log.info("Getting all items");
            List<ItemDao> itemDaoList = itemRepository.findAll();
            List<ItemDto> itemDtoList = new ArrayList<>();

            String itemImageString;

            for (ItemDao itemDao : itemDaoList) {
                itemImageString = Base64.getEncoder().encodeToString(itemDao.getItemImage());
                itemDtoList.add(ItemDto.builder()
                                .id(itemDao.getId())
                                .itemName(itemDao.getItemName())
                                .itemPrice(itemDao.getItemPrice())
                                .itemWeight(itemDao.getItemWeight())
                                .itemStock(itemDao.getItemStock())
                                .itemDescription(itemDao.getItemDescription())
                                .categoryDao(itemDao.getCategoryDao())
                                .itemImage(itemImageString)
                        .build());
            }
            return itemDtoList;
        } catch (Exception e) {
            log.error("An error occurred in getting all items. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateItem(ItemDto itemDto, MultipartFile itemImage) {
        try {
            log.info("Updating an item by id");
            Optional<ItemDao> optionalItemDao = itemRepository.findById(itemDto.getId());

            Optional<ItemDao> optionalItemDaoItemName = itemRepository.findItemName(itemDto.getItemName());

            if (optionalItemDaoItemName.isPresent() && !optionalItemDaoItemName.get().getId().equals(itemDto.getId())) {
                log.info("Item Already Exist");
                throw new AlreadyExistException("Item Already Exist");
            }

            log.info("Item found");
            ItemDao itemDao = optionalItemDao.get();
            itemDao.setItemName(itemDto.getItemName());
            itemDao.setItemPrice(itemDto.getItemPrice());
            itemDao.setItemWeight(itemDto.getItemWeight());
            itemDao.setItemStock(itemDto.getItemStock());
            itemDao.setItemDescription(itemDto.getItemDescription());
            itemDao.setCategoryDao(itemDto.getCategoryDao());

            if (itemImage.isEmpty()) {
                log.info("itemImage is null");
                itemDao.setItemImage(optionalItemDao.get().getItemImage());
            } else {
                itemDao.setItemImage(imageUploadUtil.imgUpload(itemImage));
            }

            itemRepository.save(itemDao);

        } catch (Exception e) {
            log.error("An error occurred in updating an item by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void inactivateItemById(Long id) {
        try {
            log.info("Inactivating an item by id");
            Optional<ItemDao> optionalItemDao = itemRepository.findById(id);

            if (optionalItemDao.isEmpty()) {
                log.info("Item not found");
                throw new DataNotFoundException("Item Not Found");
            }

            log.info("Item found");
            if (optionalItemDao.get().isActive()) {
                List<PackageItemDao> packageItemDaoList = packageItemRepository.findAllByItemId(id);
                for (PackageItemDao packageItemDao : packageItemDaoList) {
                    if (packageItemDao.isActive()) {
                        packageItemRepository.updateIsActive(packageItemDao.getId(), false, LocalDateTime.now());
                    }
                }
                itemRepository.updateIsActive(id, false, LocalDateTime.now());
            } else {
                itemRepository.updateIsActive(id, true, LocalDateTime.now());
            }
        } catch (Exception e) {
            log.error("An error occurred in inactivating an item by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<ItemDto> itemPage(Integer pageNumber) {
        try {
            log.info("Showing items pagination");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<ItemDao> itemDaoPage = itemRepository.pageableItem(pageable);

            return itemDaoPage.<ItemDto>map(itemDao -> ItemDto.builder()
                    .id(itemDao.getId())
                    .itemName(itemDao.getItemName())
                    .itemPrice(itemDao.getItemPrice())
                    .itemWeight(itemDao.getItemWeight())
                    .itemStock(itemDao.getItemStock())
                    .itemDescription(itemDao.getItemDescription())
                    .itemImage(Base64.getEncoder().encodeToString(itemDao.getItemImage()))
                    .categoryDao(itemDao.getCategoryDao())
                    .isActive(itemDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in showing items. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<ItemDto> searchItem(String keyword, Integer pageNumber) {
        try {
            log.info("Searching for item");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<ItemDao> itemDaoPage = itemRepository.searchItemDaoByKeyword(keyword.toLowerCase(), pageable);

            return itemDaoPage.<ItemDto>map(itemDao -> ItemDto.builder()
                    .id(itemDao.getId())
                    .itemName(itemDao.getItemName())
                    .itemPrice(itemDao.getItemPrice())
                    .itemWeight(itemDao.getItemWeight())
                    .itemStock(itemDao.getItemStock())
                    .itemDescription(itemDao.getItemDescription())
                    .itemImage(Base64.getEncoder().encodeToString(itemDao.getItemImage()))
                    .categoryDao(itemDao.getCategoryDao())
                    .isActive(itemDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in searching for items. Error {}", e.getMessage());
            throw e;
        }
    }
}
