package com.online.pasaronlineapp.util;

import com.online.pasaronlineapp.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
@Slf4j
public class ImageUploadUtil {


    public void imgUpload(MultipartFile file) {
        try {
            log.info("Uploading an image");
            Files.copy(
                    file.getInputStream(),
                    Paths.get(
                    AppConstant.ITEM_DIRECTORY + File.separator,
                            file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            log.error("An error occurred in uploading an image. Error {}", e.getMessage());
        }
    }

    public boolean checkIfExist(MultipartFile file) {
        boolean isExisted = false;

        try {
            log.info("Checking if the file exist");
            File f = new File(AppConstant.ITEM_DIRECTORY + "\\" + file.getOriginalFilename());
            isExisted = f.exists();
        } catch (Exception e) {
            log.error("An error occurred in checking the image file. Error {}", e.getMessage());
        }

        return isExisted;
    }
}
