package com.move24.domain;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class DateEntity {
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
