package com.online.pasaronlineapp.util;

import com.online.pasaronlineapp.constant.AppConstant;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseUtilTest {

    @Test
    void responseUtil_Test() {
        ResponseEntity<Object> response = ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }
}
