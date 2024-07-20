package com.move24.domain;

import com.move24.enums.ImageType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Image extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ImageType type;

    private String name;
    private String originalName;

}
