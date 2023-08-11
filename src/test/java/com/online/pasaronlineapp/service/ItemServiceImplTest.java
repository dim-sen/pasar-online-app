package com.online.pasaronlineapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.online.pasaronlineapp.domain.common.ApiResponse;
import com.online.pasaronlineapp.domain.common.ApiResponseStatus;
import com.online.pasaronlineapp.domain.dao.ItemDao;
import com.online.pasaronlineapp.domain.dto.ItemDto;
import com.online.pasaronlineapp.repository.ItemRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.online.pasaronlineapp.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ItemServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ItemServiceImplTest {
    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @MockBean
    private ModelMapper modelMapper;

    /**
     * Method under test: {@link ItemServiceImpl#createItem(ItemDto)}
     */
    @Test
    void createItemAlreadyExist_Test() {
        ItemDao itemDao = new ItemDao();
        itemDao.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        itemDao.setDeleted(true);
        itemDao.setId(1L);
        itemDao.setItemImage("Item Image");
        itemDao.setItemName("Item Name");
        itemDao.setItemPrice(42);
        itemDao.setItemWeight(42);
        itemDao.setPackageItemDaos(new ArrayList<>());
        itemDao.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

        ItemDao itemDao2 = new ItemDao();
        itemDao2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        itemDao2.setDeleted(true);
        itemDao2.setId(1L);
        itemDao2.setItemImage("Item Image");
        itemDao2.setItemName("Item Name");
        itemDao2.setItemPrice(42);
        itemDao2.setItemWeight(42);
        itemDao2.setPackageItemDaos(new ArrayList<>());
        itemDao2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

        Optional<ItemDao> ofResult = Optional.of(itemDao2);
        when(itemRepository.save(Mockito.<ItemDao>any())).thenReturn(itemDao);
        when(itemRepository.findItemName(Mockito.<String>any())).thenReturn(ofResult);

        ResponseEntity<Object> actualCreateItemResult = itemServiceImpl.createItem(new ItemDto());
        assertTrue(actualCreateItemResult.hasBody());
        assertTrue(actualCreateItemResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.CONFLICT, actualCreateItemResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualCreateItemResult.getBody()).getData());

        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateItemResult.getBody()).getStatus();
        assertEquals("Already Exists", status.getMessage());
        assertEquals("ALREADY_EXISTS", status.getCode());
        verify(itemRepository).findItemName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ItemServiceImpl#createItem(ItemDto)}
     */
    @Test
    void createItem_Test2() {
        ItemDao itemDao = new ItemDao();
        itemDao.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        itemDao.setDeleted(true);
        itemDao.setId(1L);
        itemDao.setItemImage("Item Image");
        itemDao.setItemName("Item Name");
        itemDao.setItemPrice(42);
        itemDao.setItemWeight(42);
        itemDao.setPackageItemDaos(new ArrayList<>());
        itemDao.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(itemRepository.save(Mockito.<ItemDao>any())).thenReturn(itemDao);
        when(itemRepository.findItemName(Mockito.<String>any())).thenReturn(Optional.empty());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        ResponseEntity<Object> actualCreateItemResult = itemServiceImpl.createItem(new ItemDto());
        assertTrue(actualCreateItemResult.hasBody());
        assertTrue(actualCreateItemResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualCreateItemResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualCreateItemResult.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateItemResult.getBody()).getStatus();
        assertEquals("Unknown Error", status.getMessage());
        assertEquals("UNKNOWN_ERROR", status.getCode());
        verify(itemRepository).findItemName(Mockito.<String>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<ItemDao>>any());
    }

    /**
     * Method under test: {@link ItemServiceImpl#createItem(ItemDto)}
     */
    @Test
    void createItem_Test3() {
        ItemDao itemDao = new ItemDao();
        itemDao.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        itemDao.setDeleted(true);
        itemDao.setId(1L);
        itemDao.setItemImage("Item Image");
        itemDao.setItemName("Item Name");
        itemDao.setItemPrice(42);
        itemDao.setItemWeight(42);
        itemDao.setPackageItemDaos(new ArrayList<>());
        itemDao.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(itemRepository.save(Mockito.<ItemDao>any())).thenReturn(itemDao);
        when(itemRepository.findItemName(Mockito.<String>any())).thenReturn(Optional.empty());

        ItemDao itemDao2 = new ItemDao();
        itemDao2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemDao2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        itemDao2.setDeleted(true);
        itemDao2.setId(1L);
        itemDao2.setItemImage("Creating new item");
        itemDao2.setItemName("Creating new item");
        itemDao2.setItemPrice(42);
        itemDao2.setItemWeight(42);
        itemDao2.setPackageItemDaos(new ArrayList<>());
        itemDao2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(itemDao2);
        ResponseEntity<Object> actualCreateItemResult = itemServiceImpl.createItem(new ItemDto());
        assertTrue(actualCreateItemResult.hasBody());
        assertTrue(actualCreateItemResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualCreateItemResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualCreateItemResult.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateItemResult.getBody()).getStatus();
        assertEquals("Unknown Error", status.getMessage());
        assertEquals("UNKNOWN_ERROR", status.getCode());
        verify(itemRepository).save(Mockito.<ItemDao>any());
        verify(itemRepository).findItemName(Mockito.<String>any());
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
    }

    /**
     * Method under test: {@link ItemServiceImpl#createItem(ItemDto)}
     */
    @Test
    void createItem_Test4() {
        ItemDao itemDao = new ItemDao();
        itemDao.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        itemDao.setDeleted(true);
        itemDao.setId(1L);
        itemDao.setItemImage("Item Image");
        itemDao.setItemName("Item Name");
        itemDao.setItemPrice(42);
        itemDao.setItemWeight(42);
        itemDao.setPackageItemDaos(new ArrayList<>());
        itemDao.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(itemRepository.save(Mockito.<ItemDao>any())).thenReturn(itemDao);
        when(itemRepository.findItemName(Mockito.<String>any())).thenReturn(Optional.empty());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(null);
        ResponseEntity<Object> actualCreateItemResult = itemServiceImpl.createItem(new ItemDto());
        assertTrue(actualCreateItemResult.hasBody());
        assertTrue(actualCreateItemResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualCreateItemResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualCreateItemResult.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateItemResult.getBody()).getStatus();
        assertEquals("success", status.getMessage());
        assertEquals("SUCCESS", status.getCode());
        verify(itemRepository).save(Mockito.<ItemDao>any());
        verify(itemRepository).findItemName(Mockito.<String>any());
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
    }

    /**
     * Method under test: {@link ItemServiceImpl#createItem(ItemDto)}
     */
    @Test
    void createItem_Test5() {
        ItemDao itemDao = new ItemDao();
        itemDao.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemDao.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        itemDao.setDeleted(true);
        itemDao.setId(1L);
        itemDao.setItemImage("Item Image");
        itemDao.setItemName("Item Name");
        itemDao.setItemPrice(42);
        itemDao.setItemWeight(42);
        itemDao.setPackageItemDaos(new ArrayList<>());
        itemDao.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(itemRepository.save(Mockito.<ItemDao>any())).thenReturn(itemDao);
        when(itemRepository.findItemName(Mockito.<String>any())).thenReturn(Optional.empty());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        ResponseEntity<Object> actualCreateItemResult = itemServiceImpl.createItem(null);
        assertTrue(actualCreateItemResult.hasBody());
        assertTrue(actualCreateItemResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualCreateItemResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualCreateItemResult.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualCreateItemResult.getBody()).getStatus();
        assertEquals("Unknown Error", status.getMessage());
        assertEquals("UNKNOWN_ERROR", status.getCode());
    }
}

