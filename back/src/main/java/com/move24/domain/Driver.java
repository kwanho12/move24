package com.move24.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Driver extends DateEntity {

    @Id
    @Column(name = "driver_id")
    private String id;

    private int experienceYear;

    private String content;

    private int likeCount;

}
