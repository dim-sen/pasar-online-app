package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.domain.dao.UserDao;
import com.online.pasaronlineapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load User By Username");
        Optional<UserDao> optionalUserDao = userRepository.findByPhoneNumber(username);

        if (optionalUserDao.isEmpty()) {
            log.info("Username Not Found");
            throw new UsernameNotFoundException("Could Not Find Username");
        }
        log.info("Username Found");
        return new User(
                optionalUserDao.get().getPhoneNumber(),
                optionalUserDao.get().getPassword(),
                optionalUserDao.get().getRoleDaos().stream().map(
                        roleDao -> new SimpleGrantedAuthority(
                                roleDao.getName()
                        )
                ).collect(Collectors.toList())
        );
    }
}
