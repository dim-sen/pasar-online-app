package com.online.pasaronlineapp.service;

import com.online.pasaronlineapp.domain.dto.AdminDto;
import com.online.pasaronlineapp.domain.dto.UsernamePassword;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    boolean authenticate(UsernamePassword usernamePassword);
}
