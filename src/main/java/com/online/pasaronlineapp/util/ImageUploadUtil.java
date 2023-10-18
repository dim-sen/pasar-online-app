package com.online.pasaronlineapp.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Base64;

@Component
@Slf4j
public class ImageUploadUtil {


    public byte[] imgUpload(MultipartFile file) {
        try {
            log.info("Uploading an image");
            InputStream inputStream = file.getInputStream();

            return StreamUtils.copyToByteArray(inputStream);

        } catch (Exception e) {
            log.error("An error occurred in uploading an image. Error {}", e.getMessage());
            return new byte[0];
        }
    }
}
