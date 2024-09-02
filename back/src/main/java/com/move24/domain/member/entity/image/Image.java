package com.move24.domain.member.entity.image;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.move24.domain.member.exception.ImageFileNotFoundException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.move24.domain.member.entity.image.Extension.isValidExtension;
import static com.move24.domain.member.entity.image.ImageStatus.POSTED;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Extension extension;

    private String fileName;

    private String originalName;

    private long size;

    private ImageStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    private String extractExtension(String originalName) {
        return Objects.requireNonNull(originalName)
                .substring(originalName.lastIndexOf(".") + 1);
    }

    private void isValidFile(MultipartFile imageFile) {
        Optional.ofNullable(imageFile).orElseThrow(() -> new ImageFileNotFoundException("이미지 파일을 필수로 첨부하여야 합니다.", "file"));
    }

    public static Image create(MultipartFile imageFile) {
        return new Image(imageFile);
    }

    @Builder
    private Image(MultipartFile imageFile) {
        isValidFile(imageFile);
        this.originalName = imageFile.getOriginalFilename();

        String extension = extractExtension(imageFile.getOriginalFilename());
        isValidExtension(extension);

        this.fileName = UUID.randomUUID() + "." + extension;
        this.extension = Extension.valueOf(extension.toUpperCase());
        this.size = imageFile.getSize();
        this.status = POSTED;
        this.createdDate = LocalDateTime.now();
    }
}
