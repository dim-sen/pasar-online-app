package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.domain.dao.UserDao;
import com.online.pasaronlineapp.domain.dto.UserDto;
import com.online.pasaronlineapp.repository.RoleRepository;
import com.online.pasaronlineapp.repository.UserRepository;
import com.online.pasaronlineapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<UserDao> findByUsername(String username) {
        try {
            log.info("Finding username");
            Optional<UserDao> optionalUserDao = userRepository.findByPhoneNumber(username);

            if (optionalUserDao.isEmpty()) {
                log.info("User not found");
                return Optional.empty();
            }

            log.info("User found");
            return userRepository.findByPhoneNumber(username);

        } catch (Exception e) {
            log.error("An error occurred in finding a user by id. Error {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public UserDao createUser(UserDto userDto) {
        try {
            log.info("Creating a user");

            UserDao userDao = new UserDao();
            userDao.setFirstName(userDto.getFirstName());
            userDao.setLastName(userDto.getLastName());
            userDao.setPhoneNumber(userDto.getPhoneNumber());
            userDao.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            userDao.setRoleDaos(Arrays.asList(roleRepository.findByName("ADMIN")));

            return userRepository.save(userDao);

        } catch (Exception e) {
            log.error("An error occurred in creating a user. Error {}", e.getMessage());
            throw e;
        }
    }
}
