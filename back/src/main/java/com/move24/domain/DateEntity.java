package com.move24.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class DateEntity {
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
