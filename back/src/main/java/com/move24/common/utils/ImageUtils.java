package com.move24.common.utils;

import com.move24.domain.member.exception.ImageNotSaveException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ImageUtils {
    public void upload(MultipartFile imageFile, String fileName, String uploadDir) {
        String filePath = uploadDir + "/" + fileName;
        try {
            imageFile.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new ImageNotSaveException("이미지 저장을 실패하였습니다.");
        }
    }
}
