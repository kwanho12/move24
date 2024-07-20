package com.move24.domain;

import com.move24.enums.Gender;
import com.move24.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Member extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String password;
    private String name;
    private String mail;
    private String phoneNumber;
    private String address;
    private LocalDateTime loginDate;

}
