package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import com.online.pasaronlineapp.repository.ItemRepository;
import com.online.pasaronlineapp.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResponseEntity<Object> createItem(ItemDto itemDto) {
        try {
            log.info("Creating new item");
            Optional<ItemDao> optionalItemDao =itemRepository.findItemName(itemDto.getItemName());

            if (optionalItemDao.isPresent()) {
                log.info("Item Already Exist");
                return ResponseUtil.build(AppConstant.ResponseCode.ALREADY_EXISTS, null, HttpStatus.CONFLICT);
            }

            ItemDao itemDao = modelMapper.map(itemDto, ItemDao.class);
            itemRepository.save(itemDao);

            ItemDto dto = modelMapper.map(itemDao, ItemDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating Item. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
