package com.online.pasaronlineapp.controller.rest;

import com.online.pasaronlineapp.service.impl.rest.PackageItemRestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/package-item")
public class PackageItemRestController {

    @Autowired
    private PackageItemRestServiceImpl packageItemRestService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getAllPackageItemByPackageId(@PathVariable(value = "id") Long id) {
        return packageItemRestService.getAllPackageItemByPackageId(id);
    }
}
