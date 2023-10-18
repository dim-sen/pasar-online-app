package com.online.pasaronlineapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PackageRestService {

    ResponseEntity<Object> getAllPackages();
}
