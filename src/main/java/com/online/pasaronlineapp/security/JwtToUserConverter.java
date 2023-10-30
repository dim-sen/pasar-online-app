package com.online.pasaronlineapp.security;

import com.online.pasaronlineapp.domain.dao.UserDao;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        UserDao userDao = new UserDao();
        userDao.setId(Long.valueOf(source.getSubject()));
        return new UsernamePasswordAuthenticationToken(userDao, source, Collections.EMPTY_LIST);
    }
}
