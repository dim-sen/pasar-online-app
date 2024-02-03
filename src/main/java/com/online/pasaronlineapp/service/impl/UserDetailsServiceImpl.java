package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.domain.dao.AdminDao;
import com.online.pasaronlineapp.domain.dao.AdminDetailsDao;
import com.online.pasaronlineapp.domain.dao.PembeliDao;
import com.online.pasaronlineapp.domain.dao.PembeliDetailDao;
import com.online.pasaronlineapp.repository.AdminRepository;
import com.online.pasaronlineapp.repository.PembeliRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PembeliRepository pembeliRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load User By Username");

        Optional<PembeliDao> pembeliOptional = pembeliRepository.findByPhoneNumber(username);
        if (pembeliOptional.isPresent()) {
            PembeliDao pembeliDao = pembeliOptional.get();
            log.info("Pembeli found");
            return PembeliDetailDao.build(pembeliDao);
        }

        Optional<AdminDao> adminOptional = adminRepository.findByUsername(username);
        if (adminOptional.isPresent()) {
            AdminDao adminDao = adminOptional.get();
            log.info("Admin found");
            return AdminDetailsDao.build(adminDao);
        }

        throw new UsernameNotFoundException("User Not Found With Username: " + username);
    }
}

