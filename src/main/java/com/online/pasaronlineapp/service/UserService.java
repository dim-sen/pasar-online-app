package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dao.PembeliDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<PembeliDao> findByUsername(String username);

//    UserDao createUser(UserDto userDto);
}
