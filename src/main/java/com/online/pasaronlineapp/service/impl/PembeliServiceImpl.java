package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.config.JwtTokenProvider;
import com.online.pasaronlineapp.constant.AppConstant;
import com.online.pasaronlineapp.domain.dao.PembeliDao;
import com.online.pasaronlineapp.domain.dao.PembeliDetailDao;
import com.online.pasaronlineapp.domain.dto.JwtResponse;
import com.online.pasaronlineapp.domain.dto.PembeliDto;
import com.online.pasaronlineapp.domain.dto.UsernamePassword;
import com.online.pasaronlineapp.repository.RoleRepository;
import com.online.pasaronlineapp.repository.PembeliRepository;
import com.online.pasaronlineapp.service.PembeliService;
import com.online.pasaronlineapp.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PembeliServiceImpl implements PembeliService {

    @Autowired
    private PembeliRepository pembeliRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Object> register(PembeliDto pembeliDto) {
        try {
            log.info("Creating a new user");

            Optional<PembeliDao> optionalPembeliDao = pembeliRepository.findByPhoneNumber(pembeliDto.getPhoneNumber());

            if (optionalPembeliDao.isPresent()) {
                log.info("Username already exists");
                return ResponseUtil.build(AppConstant.ResponseCode.ALREADY_EXISTS, null, HttpStatus.CONFLICT);
            }

            PembeliDao pembeliDao = PembeliDao.builder()
                    .firstName(pembeliDto.getFirstName())
                    .lastName(pembeliDto.getLastName())
                    .phoneNumber(pembeliDto.getPhoneNumber())
                    .password(passwordEncoder.encode(pembeliDto.getPassword()))
                    .build();

            pembeliRepository.save(pembeliDao);

            PembeliDto dto = PembeliDto.builder()
                    .id(pembeliDao.getId())
                    .firstName(pembeliDao.getFirstName())
                    .lastName(pembeliDao.getLastName())
                    .phoneNumber(pembeliDao.getPhoneNumber())
                    .password(pembeliDao.getPassword())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occurred in creating a new User. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> authenticateUser(UsernamePassword usernamePassword) {
        try {
            log.info("Generating JWT token");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usernamePassword.getUsername(), usernamePassword.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenProvider.generateToken(authentication);

            PembeliDetailDao pembeliDetailDao = (PembeliDetailDao) authentication.getPrincipal();

            JwtResponse response = JwtResponse.builder()
                    .userId(pembeliDetailDao.getPembeliDao().getId())
                    .username(usernamePassword.getUsername())
                    .token(jwt)
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, response, HttpStatus.OK);

        } catch (Exception badCredentialsException) {
            log.error("An error occurred in Authenticate User. Error {}", badCredentialsException.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }
    }
}
