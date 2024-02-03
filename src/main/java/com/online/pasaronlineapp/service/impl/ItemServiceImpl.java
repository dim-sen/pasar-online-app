package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.BarangDao;
import com.online.pasaronlineapp.domain.dao.PackageBarangDao;
import com.online.pasaronlineapp.domain.dto.BarangDto;
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

    @Override
    public void createItem(BarangDto barangDto, MultipartFile itemImage) {
        try {
            log.info("Creating new item");
            Optional<BarangDao> optionalItemDao = itemRepository.findItemName(barangDto.getBarangName());

            if (optionalItemDao.isPresent()) {
                log.info("Item Already Exist");
                throw new AlreadyExistException("Item Already Exist");
            }

            BarangDao barangDao = BarangDao.builder()
                    .barangName(barangDto.getBarangName())
                    .barangPrice(barangDto.getBarangPrice())
                    .barangWeight(barangDto.getBarangWeight())
                    .barangStock(barangDto.getBarangStock())
                    .barangDescription(barangDto.getBarangDescription())
                    .barangImage(imageUploadUtil.imgUpload(itemImage))
                    .categoryDao(barangDto.getCategoryDao())
                    .build();
            itemRepository.save(barangDao);

        } catch (Exception e) {
            log.error("An error occurred in creating Item. Error: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public BarangDao findItemById(Long id) {
        try {
            log.info("Finding an item by id");
            Optional<BarangDao> optionalItemDao = itemRepository.findById(id);

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
    public List<BarangDto> getAllItems() {
        try {
            log.info("Getting all items");
            List<BarangDao> itemDaoList = itemRepository.findAll();
            List<BarangDto> itemDtoList = new ArrayList<>();

            String itemImageString;

            for (BarangDao barangDao : itemDaoList) {
                itemImageString = Base64.getEncoder().encodeToString(barangDao.getBarangImage());
                itemDtoList.add(BarangDto.builder()
                                .id(barangDao.getId())
                                .barangName(barangDao.getBarangName())
                                .barangPrice(barangDao.getBarangPrice())
                                .barangWeight(barangDao.getBarangWeight())
                                .barangStock(barangDao.getBarangStock())
                                .barangDescription(barangDao.getBarangDescription())
                                .categoryDao(barangDao.getCategoryDao())
                                .barangImage(itemImageString)
                        .build());
            }
            return itemDtoList;
        } catch (Exception e) {
            log.error("An error occurred in getting all items. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateItem(BarangDto barangDto, MultipartFile itemImage) {
        try {
            log.info("Updating an item by id");
            Optional<BarangDao> optionalItemDao = itemRepository.findById(barangDto.getId());

            Optional<BarangDao> optionalItemDaoItemName = itemRepository.findItemName(barangDto.getBarangName());

            if (optionalItemDaoItemName.isPresent() && !optionalItemDaoItemName.get().getId().equals(barangDto.getId())) {
                log.info("Item Already Exist");
                throw new AlreadyExistException("Item Already Exist");
            }

            log.info("Item found");
            BarangDao barangDao = optionalItemDao.get();
            barangDao.setBarangName(barangDto.getBarangName());
            barangDao.setBarangPrice(barangDto.getBarangPrice());
            barangDao.setBarangWeight(barangDto.getBarangWeight());
            barangDao.setBarangStock(barangDto.getBarangStock());
            barangDao.setBarangDescription(barangDto.getBarangDescription());
            barangDao.setCategoryDao(barangDto.getCategoryDao());

            if (itemImage.isEmpty()) {
                log.info("itemImage is null");
                barangDao.setBarangImage(optionalItemDao.get().getBarangImage());
            } else {
                barangDao.setBarangImage(imageUploadUtil.imgUpload(itemImage));
            }

            barangDao.setActive(barangDto.isActive());

            itemRepository.save(barangDao);

        } catch (Exception e) {
            log.error("An error occurred in updating an item by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<BarangDto> itemPage(Integer pageNumber) {
        try {
            log.info("Showing items pagination");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<BarangDao> barangDaoPage = itemRepository.pageableItem(pageable);

            return barangDaoPage.<BarangDto>map(barangDao -> BarangDto.builder()
                    .id(barangDao.getId())
                    .barangName(barangDao.getBarangName())
                    .barangPrice(barangDao.getBarangPrice())
                    .barangWeight(barangDao.getBarangWeight())
                    .barangStock(barangDao.getBarangStock())
                    .barangDescription(barangDao.getBarangDescription())
                    .barangImage(Base64.getEncoder().encodeToString(barangDao.getBarangImage()))
                    .categoryDao(barangDao.getCategoryDao())
                    .isActive(barangDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in showing items. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<BarangDto> searchItem(String keyword, Integer pageNumber) {
        try {
            log.info("Searching for item");
            Pageable pageable = PageRequest.of(pageNumber, AppConstant.PAGE_MAX, Sort.by(Sort.Order.desc("isActive"), Sort.Order.asc("id")));

            Page<BarangDao> barangDaoPage = itemRepository.searchItemDaoByKeyword(keyword.toLowerCase(), pageable);

            return barangDaoPage.<BarangDto>map(barangDao -> BarangDto.builder()
                    .id(barangDao.getId())
                    .barangName(barangDao.getBarangName())
                    .barangPrice(barangDao.getBarangPrice())
                    .barangWeight(barangDao.getBarangWeight())
                    .barangStock(barangDao.getBarangStock())
                    .barangDescription(barangDao.getBarangDescription())
                    .barangImage(Base64.getEncoder().encodeToString(barangDao.getBarangImage()))
                    .categoryDao(barangDao.getCategoryDao())
                    .isActive(barangDao.isActive())
                    .build());
        } catch (Exception e) {
            log.error("An error occurred in searching for items. Error {}", e.getMessage());
            throw e;
        }
    }
}
