package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.domain.dao.AdminDao;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load Pembeli By Username");
        PembeliDao pembeliDao = pembeliRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found With Username: " + username));

        log.info("Username found");
        return PembeliDetailDao.build(pembeliDao);
    }
}
