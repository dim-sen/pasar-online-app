package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.domain.dao.AdminDao;
import com.online.pasaronlineapp.domain.dao.UserDao;
import com.online.pasaronlineapp.repository.AdminRepository;
import com.online.pasaronlineapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load Admin By Username");
        Optional<AdminDao> optionalAdminDao = adminRepository.findByUsername(username);

        if (optionalAdminDao.isEmpty()) {
            log.info("Username Not Found");
            throw new UsernameNotFoundException("Could Not Find Username");
        }

        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(
                new SimpleGrantedAuthority(optionalAdminDao.get().getRole().getRoleName())
        );

        log.info("Username Found");
        return new User(
                optionalAdminDao.get().getUsername(),
                optionalAdminDao.get().getPassword(),
                grantedAuthorities

        );
    }
}
