package com.move24.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Role extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    private String name;

}
