package com.online.pasaronlineapp.service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PackageItemRestService {

    ResponseEntity<Object> getAllPackageItemByPackageId(Long id);
}
