package com.move24.domain;

import com.move24.enums.Extension;
import com.move24.exception.exception.FileNameNotValidException;
import com.move24.exception.exception.ImageNotSaveException;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.move24.enums.Extension.*;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Image extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private final Extension extension;

    private final String fileName;
    private final String originalName;

    public Image(MultipartFile file) {

        super(LocalDateTime.now(), LocalDateTime.now());
        this.originalName = file.getOriginalFilename();
        if (this.originalName == null || !this.originalName.contains(".")) {
            throw new FileNameNotValidException("유효하지 않은 파일 이름입니다.");
        }

        String extension = originalName.substring(originalName.lastIndexOf(".") + 1);
        isValidExtension(extension);
        this.extension = valueOf(extension.toUpperCase());

        String uniqueName = UUID.randomUUID().toString();
        this.fileName = uniqueName + "." + extension;

    }

    public void upload(MultipartFile file, String fileName, String uploadDir) {
        // 파일 저장
        String filePath = uploadDir + "/" + fileName;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new ImageNotSaveException("이미지 저장을 실패하였습니다.");
        }
    }
}
