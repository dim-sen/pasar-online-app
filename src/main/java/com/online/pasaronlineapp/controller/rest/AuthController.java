package com.online.pasaronlineapp.controller.rest;

import com.online.pasaronlineapp.domain.dto.PembeliDto;
import com.online.pasaronlineapp.domain.dto.UsernamePassword;
import com.online.pasaronlineapp.service.impl.PembeliServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private PembeliServiceImpl pembeliService;

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@Valid @RequestBody PembeliDto pembeliDto) {
        return pembeliService.register(pembeliDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> generateToken(@Valid @RequestBody UsernamePassword usernamePassword) {
        return pembeliService.authenticateUser(usernamePassword);
    }
}
