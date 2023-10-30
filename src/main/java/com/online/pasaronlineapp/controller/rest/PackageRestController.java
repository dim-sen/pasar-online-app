package com.online.pasaronlineapp.controller.rest;

import com.online.pasaronlineapp.service.impl.rest.PackageRestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/package")
public class PackageRestController {

    @Autowired
    private PackageRestServiceImpl packageRestService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllPackages() {
        return packageRestService.getAllPackages();
    }
}
