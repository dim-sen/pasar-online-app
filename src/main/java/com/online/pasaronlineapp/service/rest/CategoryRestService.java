package com.online.pasaronlineapp.service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryRestService {

    ResponseEntity<Object> getAllCategories();
}
