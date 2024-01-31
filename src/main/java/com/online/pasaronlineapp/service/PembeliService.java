package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.PembeliDao;
import com.online.pasaronlineapp.domain.dto.PembeliDto;
import com.online.pasaronlineapp.domain.dto.UsernamePassword;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PembeliService {

    ResponseEntity<Object> register(PembeliDto pembeliDto);

    ResponseEntity<Object> authenticateUser(UsernamePassword usernamePassword);
}
