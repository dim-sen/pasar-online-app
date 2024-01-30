package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.domain.dao.PembeliDao;
import com.online.pasaronlineapp.repository.RoleRepository;
import com.online.pasaronlineapp.repository.UserRepository;
import com.online.pasaronlineapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public Optional<PembeliDao> findByUsername(String username) {
        try {
            log.info("Finding username");
            Optional<PembeliDao> optionalUserDao = userRepository.findByPhoneNumber(username);

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

//    @Override
//    public UserDao createUser(UserDto userDto) {
//        try {
//            log.info("Creating a user");
//
//            Optional<RoleDao> optionalRoleDao = roleRepository.findByName(AppConstant.Role.ADMIN.getRoleName());
//
//            if (optionalRoleDao.isEmpty()) {
//                RoleDao roleDao = RoleDao.builder()
//                        .name(AppConstant.Role.ADMIN.getRoleName())
//                        .build();
//
//                optionalRoleDao = Optional.of(roleRepository.save(roleDao));
//            }
//
//            UserDao userDao = new UserDao();
//            userDao.setFirstName(userDto.getFirstName());
//            userDao.setLastName(userDto.getLastName());
//            userDao.setPhoneNumber(userDto.getPhoneNumber());
//            userDao.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
//            userDao.setRoleDao(optionalRoleDao.orElseThrow(() -> new RuntimeException("Role not found")));
//
//            return userRepository.save(userDao);
//
//        } catch (Exception e) {
//            log.error("An error occurred in creating a user. Error {}", e.getMessage());
//            throw e;
//        }
//    }
}
