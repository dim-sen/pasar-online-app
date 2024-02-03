package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.config.JwtTokenProvider;
import com.online.pasaronlineapp.domain.dao.AdminDao;
import com.online.pasaronlineapp.domain.dto.AdminDto;
import com.online.pasaronlineapp.domain.dto.UsernamePassword;
import com.online.pasaronlineapp.repository.AdminRepository;
import com.online.pasaronlineapp.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean authenticate(UsernamePassword usernamePassword) {
        return false;
    }
}
