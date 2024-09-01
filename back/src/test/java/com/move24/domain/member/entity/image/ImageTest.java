package com.move24.domain.member.entity.image;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Slf4j
class ImageTest {

    @DisplayName("이미지 파일 업로드 시 업로드한 이미지의 확장자를 추출한다.")
    @Test
    void extractExtension() {
        // given
        MultipartFile imageFile = createImageFile("testFile.jpeg");
        log.info("size : " + imageFile.getSize());

        // when
        Image image = Image.create(imageFile);

        // then
        Extension extension = image.getExtension();
        assertThat(extension).isEqualTo(Extension.valueOf("jpeg".toUpperCase()));
    }

    private MultipartFile createImageFile(String originalFileName) {
        return new MockMultipartFile(
                "file",
                originalFileName,
                IMAGE_JPEG_VALUE,
                "test file".getBytes()
        );
    }
}