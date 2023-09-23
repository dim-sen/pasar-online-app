package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.UserDao;
import com.online.pasaronlineapp.domain.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<UserDao> findByUsername(String username);

//    UserDao createUser(UserDto userDto);
}
