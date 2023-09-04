package com.online.pasaronlineapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ItemRestService {

    ResponseEntity<Object> getItemById(Long id);

    ResponseEntity<Object> getAllItems();
}
